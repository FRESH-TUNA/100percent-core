package com.main.koko_main_api.dtos.album;

import com.main.koko_main_api.domains.Album;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */

@Getter
@NoArgsConstructor
public class AlbumRequestDto {
    private String title;

    public Album toEntity() {
        return Album.builder().title(title).build();
    }

    @Builder
    public AlbumRequestDto(String title) {
        this.title = title;
    }
}
