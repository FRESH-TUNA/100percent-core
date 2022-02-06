package com.main.koko_main_api.repositories.album;

import com.main.koko_main_api.domains.Album;

import java.util.List;

public interface AlbumCustomRepository {
    /*
     * select m from Member m
     */
    List<Album> findAll();

    Album find(Long id);
}
