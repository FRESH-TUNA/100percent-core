package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.domains.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MusicSearchRepository<T, ID> {
    Page<T> findAll(Pageable pageable, Album album_id);

    Optional<T> findById(ID id);
}
