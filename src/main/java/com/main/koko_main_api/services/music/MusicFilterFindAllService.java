package com.main.koko_main_api.services.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.music.MusicEntityToServiceDto;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsDto;
import com.main.koko_main_api.repositories.music.MusicRepository;

import com.main.koko_main_api.repositories.pattern.PatternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicFilterFindAllService {
    private final MusicRepository musicRepository;
    private final PatternRepository patternRepository;

//    public List<MusicEntityToServiceDto> call(MusicRequestParams params) {
//        String mode = params.getMode();
//        if(mode.equals("difficulty")) return difficultyFindAll(params);
//        if(mode.equals("level")) return levelFindAll(params);
//
//        return difficultyFindAll(params);
//    }

    /*
     * playable/playtype + difficulty 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */

    public Page<MusicEntityToServiceDto> findAllByDifficulty(Long play_type_id, Long difficulty_id) {
        // pattern을 가지고 온다.
        List<Pattern> patterns = patternRepository.findAllByPlayTypeAndDifficulty(play_type_id, difficulty_id);

        List<Long> music_ids = getMusicIdsFromPatterns(patterns);

        List<Music> musics = musicRepository.findAllByIds(music_ids);

        Page<Music> music_page = musicsToSinglePage(musics);

        return add_patterns_and_get_music_page(music_page, patterns);
    }

    /*
     * playable/playtype + level 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */
    public Page<MusicEntityToServiceDto> findAllByLevel(Long play_type_id, Integer level) {
        // pattern을 가지고 온다.
        List<Pattern> patterns = patternRepository.findAllByPlayTypeAndLevel(play_type_id, level);

        List<Long> music_ids = getMusicIdsFromPatterns(patterns);

        List<Music> musics = musicRepository.findAllByIds(music_ids);

        Page<Music> music_page = musicsToSinglePage(musics);

        return add_patterns_and_get_music_page(music_page, patterns);
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
