package com.main.koko_main_api.repositories.playable;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlayableSearchRepository<T, ID> {
    List<T> findAll();

    Page<T> findAll(Pageable pageable, Predicate predicate);

    Optional<T> findById(ID id);
}
