package com.main.koko_main_api.services.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;

import com.main.koko_main_api.dtos.music.*;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsDto;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final PatternRepository patternRepository;

    private final MusicSaveDtoDeassembler deassembler;
    private final MusicDtoAssembler showAssembler;

    private final PagedResourcesAssembler<MusicEntityToServiceDto> pageAssembler;

    /*
     * playtype_id가 반드시 전달되어서 필터링된다.
     */
    public PagedModel<MusicDto> findAll(Pageable pageable, MusicRequestParams params) {
        Page<MusicEntityToServiceDto> result;
        String mode = params.getMode();
        Long album_id = params.getAlbum(), play_type_id = params.getPlay_type();

        if(mode == null || mode.equals("db")) {
            if(album_id == null) result = findAll(pageable, play_type_id);
            else result = findAllByAlbum(pageable, play_type_id, album_id);
        }
        else result = null;
        return pageAssembler.toModel(result, showAssembler);
    }

    public Page<MusicEntityToServiceDto> findAll(Pageable pageable, Long play_type_id) {
        Page<Music> music_page = musicRepository.findAll(pageable);

        List<Long> music_ids = music_page.map(m -> m.getId()).toList();

        List<Pattern> patterns = patternRepository
                .findAllByPlayTypeAndMusics(music_ids, play_type_id);

        return add_patterns_and_get_music_page(music_page, patterns);
    }

    public Page<MusicEntityToServiceDto> findAllByAlbum(Pageable pageable, Long play_type_id, Long album_id) {
        Page<Music> music_page = musicRepository.findAllByAlbum(pageable, album_id);

        List<Long> music_ids = music_page.map(m -> m.getId()).toList();

        List<Pattern> patterns = patternRepository
                .findAllByPlayTypeAndMusics(music_ids, play_type_id);

        return add_patterns_and_get_music_page(music_page, patterns);
    }

    /*
     * create
     */
    @Transactional
    public MusicDto save(MusicSaveDto musicSavePayloadDto) {
        Music music = deassembler.toEntity(musicSavePayloadDto);
        MusicEntityToServiceDto dto = new MusicEntityToServiceDto(
                musicRepository.save(music));
        return showAssembler.toModel(dto);
    }

    /*
     * helper
     */
    private Page<MusicEntityToServiceDto> add_patterns_and_get_music_page(Page<Music> music_page,
                                              List<Pattern> patterns) {
        HashMap<Long, MusicEntityToServiceDto> entity_dto_mapper = new HashMap<>();
        Page<MusicEntityToServiceDto> result = music_page
                .map(m -> {
                    MusicEntityToServiceDto dto = new MusicEntityToServiceDto(m);
                    entity_dto_mapper.put(m.getId(), dto);
                    return dto;
                });

        for(Pattern p: patterns) {
            entity_dto_mapper.get(p.getMusic().getId()).addMusicPatternDto(new MusicPatternsDto(p));
        }

        return result;
    }
}
