package com.main.koko_main_api.domainDtos.album;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlbumsUpdateRequestDto {
    private String title;

    @Builder
    public AlbumsUpdateRequestDto(String title) {
        this.title = title;
    }
}
