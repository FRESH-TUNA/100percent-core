package com.main.koko_main_api.dtos.music;


import com.main.koko_main_api.domains.Album;

public class MusicAlbumDto {
    private Long id;
    private String title;

    public MusicAlbumDto(Album a) {
        id = a.getId();
        title = a.getTitle();
    }
}
