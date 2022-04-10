package com.main.koko_main_api.repositories.music;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MusicSearchRepository<T, ID> {
    Page<T> findAllByAlbum(Pageable pageable, Long album_id);

    List<T> findAllByIds(List<ID> ids);
}
