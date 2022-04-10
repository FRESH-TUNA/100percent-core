package com.main.koko_main_api.repositories.pattern;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;

import java.util.List;

public interface PatternSearchRepository<T, ID> {
    List<Pattern> findAllByPlayTypeAndMusics(List<Music> musics, Long play_type_id);

    List<Pattern> findAllByPlayTypeAndDifficulty(Long play_type_id, Long difficulty_id);

    List<Pattern> findAllByPlayTypeAndLevel(Long play_type_id, Integer level);
}
