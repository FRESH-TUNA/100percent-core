package com.main.koko_main_api.dtos.music.album;

import com.main.koko_main_api.domains.Album;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class MusicAlbumPayloadDto extends RepresentationModel<MusicAlbumPayloadDto> {
    private Long id;
    private String title;

    public MusicAlbumPayloadDto(Album a)  {
        id = a.getId();
        title = a.getTitle();
    }
}
