package com.main.koko_main_api.services.music;

import com.main.koko_main_api.assemblers.music.MusicAssembler;
import com.main.koko_main_api.assemblers.music.MusicDeassembler;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.repositories.music.MusicRepository;

import com.main.koko_main_api.repositories.pattern.PatternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicFilterFindAllService {
    private final MusicRepository musicRepository;
    private final PatternRepository patternRepository;

    private final MusicDeassembler deassembler;
    private final MusicAssembler showAssembler;
    private final PagedResourcesAssembler<Music> pageAssembler;

    /*
     * playable/playtype + difficulty 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */

    public PagedModel<MusicResponseDto> findAllByDifficulty(Long play_type_id, Long difficulty_id) {
        // pattern을 가지고 온다.
        List<Pattern> patterns = patternRepository.findAllByPlayTypeAndDifficulty(play_type_id, difficulty_id);

        List<Long> music_ids = getMusicIdsFromPatterns(patterns);

        List<Music> musics = musicRepository.findAllByIds(music_ids);

        Page<Music> music_page = musicsToSinglePage(musics);

        Page<Music> res = add_patterns_and_get_music_page(music_page, patterns);

        return pageAssembler.toModel(res, showAssembler);
    }

    /*
     * playable/playtype + level 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */
    public PagedModel<MusicResponseDto> findAllByLevel(Long play_type_id, Integer level) {
        // pattern을 가지고 온다.
        List<Pattern> patterns = patternRepository.findAllByPlayTypeAndLevel(play_type_id, level);

        List<Long> music_ids = getMusicIdsFromPatterns(patterns);

        List<Music> musics = musicRepository.findAllByIds(music_ids);

        Page<Music> music_page = musicsToSinglePage(musics);

        Page<Music> res = add_patterns_and_get_music_page(music_page, patterns);

        return pageAssembler.toModel(res, showAssembler);
    }

    /*
     * helper
     */
    private List<Long> getMusicIdsFromPatterns(List<Pattern> patterns) {
        return patterns.stream().map(p -> p.getMusic().getId())
                .distinct().sorted().collect(Collectors.toList());
    }

    private Page<Music> musicsToSinglePage(List<Music> musics) {
        return new PageImpl(musics, PageRequest.of(0, musics.size()), musics.size());
    }

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
}
