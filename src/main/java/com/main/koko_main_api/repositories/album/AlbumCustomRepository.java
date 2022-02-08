package com.main.koko_main_api.repositories.album;

import com.main.koko_main_api.domains.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumCustomRepository {
    List<Album> findAll();

    Optional<Album> findById(Long id);

    Album save(Album album);

    void deleteById(Long id);

    void deleteAll();

    void flush();
}
