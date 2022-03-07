package com.main.koko_main_api.repositories.pattern;

import com.main.koko_main_api.domains.Pattern;

import java.util.List;

public interface PatternSearchRepository<T, ID> {
    List<Pattern> findAllByPlayTypeAndMusics(List<Long> music_ids, Long play_type_id);
}
