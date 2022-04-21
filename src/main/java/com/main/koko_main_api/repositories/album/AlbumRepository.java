package com.main.koko_main_api.repositories.album;

import com.main.koko_main_api.domains.Album;
import java.util.List;
import java.util.Optional;

public interface AlbumRepository {
    List<Album> findAll();

    void flush();

    Optional<Album> findById(Long id);

    Album getById(Long id);

    void deleteById(Long id);

    void deleteAll();

    Album save(Album album);

    Album saveAndFlush(Album album);
}
