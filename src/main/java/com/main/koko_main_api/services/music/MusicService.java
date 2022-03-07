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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    private final MusicAddPatternsResultService musicAddPatternsResultService;

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

        return musicAddPatternsResultService.call(music_page, patterns);
    }

    public Page<MusicEntityToServiceDto> findAllByAlbum(Pageable pageable, Long play_type_id, Long album_id) {
        HashMap<Music, MusicEntityToServiceDto> entity_dto_mapper = new HashMap<>();
        List<Long> music_ids = new ArrayList<>();
        Page<Music> music_page = musicRepository.findAllByAlbum(pageable, album_id);

        Page<MusicEntityToServiceDto> results = music_page.map(
                m -> {
                    MusicEntityToServiceDto dto = new MusicEntityToServiceDto(m);
                    entity_dto_mapper.put(m, dto);
                    music_ids.add(m.getId());
                    return dto;
                });

        List<Pattern> patterns = patternRepository
                .findAllByPlayTypeAndMusics(music_ids, play_type_id);

        addMusicPatternDtos(entity_dto_mapper, patterns);
        return results;
    }

    /*
     * helpers
     */
    private void addMusicPatternDtos(HashMap<Music, MusicEntityToServiceDto> mapper,
                                     List<Pattern> patterns) {
        for(Pattern p: patterns) {
            mapper.get(p.getMusic()).addMusicPatternDto(new MusicPatternsDto(p));
        }
    }


    /*
     * playable/playType + album 에따른 필터링을 지원하는 findall 메소드
     * difficulty 기준 필터링
     */
//    private Page<MusicEntityToServiceDto> findAll(Pageable pageable, Long Album_id) {
//        Page<Music> musics_page = musicRepository.findAll(pageable, new Album());
//        Long play_type_id = params.getPlay_type();
//        List<Music> musics = musics_page.getContent();
//        List<MusicEntityToServiceDto> musicEntityToServiceDtos = new ArrayList<>();
//
//        for(Music m : musics) {
//            List<MusicPlayablesDto> filtered_playables = new ArrayList<>();
//            List<Pattern> patterns = m.getPatterns();
//
//            // play_type filtering
//            for(Pattern pattern : patterns)
//                if(pattern.getPlayType().getId().equals(play_type_id))
//                    filtered_playables.add(new MusicPlayablesDto(pattern));
//
//            musicEntityToServiceDtos.add(new MusicEntityToServiceDto(m, filtered_playables));
//        }
//
//        return new PageImpl<>(musicEntityToServiceDtos, pageable, musics_page.getTotalElements());
//    }

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
    private Page<MusicEntityToServiceDto> list_to_single_page(
            List<MusicEntityToServiceDto> dtos) {
        int list_size = dtos.size();
        return new PageImpl<>(dtos, PageRequest.of(0, list_size), list_size);
    }
}
