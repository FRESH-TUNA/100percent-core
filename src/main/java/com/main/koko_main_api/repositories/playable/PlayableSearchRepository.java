package com.main.koko_main_api.repositories.playable;

import com.main.koko_main_api.domains.Playable;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlayableSearchRepository {
    List<Playable> findAll();

    Page<Playable> findAll(Pageable pageable, Predicate predicate);

    Optional<Playable> findById(Long id);
}
