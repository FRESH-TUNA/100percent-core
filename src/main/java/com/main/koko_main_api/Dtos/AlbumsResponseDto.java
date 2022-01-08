package com.main.koko_main_api.Dtos;

import com.main.koko_main_api.Models.Albums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AlbumsResponseDto {
    private Long id;
    private String title;

    public AlbumsResponseDto(Albums entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }
}
