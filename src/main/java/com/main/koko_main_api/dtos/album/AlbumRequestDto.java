package com.main.koko_main_api.dtos.album;

import com.main.koko_main_api.domains.Album;
import lombok.Getter;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */

@Getter
public class AlbumRequestDto {

    private String title;

    public AlbumRequestDto(String title) {
        this.title = title;
    }

    public Album toEntity() {
        return Album.builder().title(title).build();
    }

    public Album toEntity(Long id) {
        return Album.builder().title(title).id(id).build();
    }
}
