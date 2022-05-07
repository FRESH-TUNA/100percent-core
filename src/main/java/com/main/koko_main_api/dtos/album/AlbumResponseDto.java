package com.main.koko_main_api.dtos.album;

import com.main.koko_main_api.domains.Album;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AlbumResponseDto extends RepresentationModel<AlbumResponseDto> {
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<AlbumMusicsResponseDto> musics;

    public AlbumResponseDto(Album entity, List<AlbumMusicsResponseDto> musics) {
        this.title = entity.getTitle();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.musics = musics;
    }
}
