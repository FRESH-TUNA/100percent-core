package com.main.koko_main_api.repositories.pattern;

import java.util.Optional;

public interface PatternSearchRepository<T, ID> {
    Optional<T> findById(ID id);
}
