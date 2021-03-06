package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.domains.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MusicSearchRepository<T, ID> {
    Page<T> findAllByAlbum(Pageable pageable, Long album_id);

    Page<Music> findAllByTitle(Pageable pageable, String title);

    List<T> findAll(List<T> musics);

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(ID id);
}
