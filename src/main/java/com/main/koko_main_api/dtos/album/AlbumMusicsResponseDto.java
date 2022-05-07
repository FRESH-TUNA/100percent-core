package com.main.koko_main_api.dtos.album;
import com.main.koko_main_api.domains.Music;
import org.springframework.hateoas.RepresentationModel;


public class AlbumMusicsResponseDto extends RepresentationModel<AlbumMusicsResponseDto> {
    private String title;

    public AlbumMusicsResponseDto(Music m)  {
        title = m.getTitle();
    }
}
