package com.main.koko_main_api.services.music;

import com.main.koko_main_api.assemblers.music.MusicAssembler;
import com.main.koko_main_api.assemblers.music.MusicDeassembler;
import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;

import com.main.koko_main_api.dtos.music.*;
import com.main.koko_main_api.dtos.music.bpm.MusicBpmsRequestDto;
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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final PatternRepository patternRepository;

    private final MusicDeassembler deassembler;
    private final MusicAssembler showAssembler;
    private final PagedResourcesAssembler<Music> pageAssembler;

    public PagedModel<MusicResponseDto> findAll(Pageable pageable, Long play_type_id) {
        Page<Music> music_page = musicRepository.findAll(pageable);

        List<Pattern> patterns = patternRepository
                .findAllByPlayTypeAndMusics(music_page.getContent(), play_type_id);

        Page<Music> res = add_patterns_and_get_music_page(music_page, patterns);
        return pageAssembler.toModel(res, showAssembler);
    }

    public PagedModel<MusicResponseDto> findAllByAlbum(Pageable pageable, Long play_type_id, Long album_id) {
        Page<Music> music_page = musicRepository.findAllByAlbum(pageable, album_id);

        List<Pattern> patterns = patternRepository
                .findAllByPlayTypeAndMusics(music_page.getContent(), play_type_id);

        Page<Music> res = add_patterns_and_get_music_page(music_page, patterns);
        return pageAssembler.toModel(res, showAssembler);
    }

    /*
     * create
     */
    @Transactional
    public MusicDto save(MusicRequestDto musicSavePayloadDto) {
        Music music = deassembler.toEntity(musicSavePayloadDto);
        set_bpms(music, musicSavePayloadDto.getBpms());
        return new MusicDto(musicRepository.save(music));
    }

    /*
     * update
     */
    @Transactional
    public MusicDto update(MusicRequestDto musicSavePayloadDto) {
        Music music = deassembler.toEntity(musicSavePayloadDto);
        return new MusicDto(musicRepository.save(music));
    }


    /*
     * helper
     */
    private Page<Music> add_patterns_and_get_music_page(Page<Music> music_page,
                                                           List<Pattern> patterns) {
        HashMap<Music, Music> entity_dto_mapper = new HashMap<>();
        Page<Music> result = music_page
                .map(m -> {
                    Music dto = Music.builder().id(m.getId())
                            .album(m.getAlbum()).title(m.getTitle()).build();
                    entity_dto_mapper.put(m, dto);
                    return dto;
                });
        for(Pattern p: patterns) entity_dto_mapper.get(p.getMusic()).add_pattern(p);
        return result;
    }

    private void set_bpms(Music music, List<MusicBpmsRequestDto> bpm_datas) {
        List<Bpm> bpms = bpm_datas.stream()
                .map(b -> Bpm.builder().value(b.getValue()).music(music).build())
                .collect(Collectors.toList());
        music.add_bpms(bpms);
    }
}
