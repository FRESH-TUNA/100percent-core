package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MusicSearchRepository<T, ID> {
    List<T> findAll(MusicRequestParams params);

    Page<T> findAll(Pageable pageable, MusicRequestParams params);

    Optional<T> findById(ID id);
}
