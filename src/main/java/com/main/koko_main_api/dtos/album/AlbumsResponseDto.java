package com.main.koko_main_api.dtos.album;

import com.main.koko_main_api.domains.Album;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class AlbumsResponseDto extends RepresentationModel<AlbumsResponseDto> {
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public AlbumsResponseDto(Album entity) {
        this.title = entity.getTitle();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
