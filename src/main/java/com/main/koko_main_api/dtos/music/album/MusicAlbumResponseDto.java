package com.main.koko_main_api.dtos.music.album;

import com.main.koko_main_api.domains.Album;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class MusicAlbumResponseDto extends RepresentationModel<MusicAlbumResponseDto> {
    private Long id;
    private String title;

    public MusicAlbumResponseDto(Album a)  {
        id = a.getId();
        title = a.getTitle();
    }
}
