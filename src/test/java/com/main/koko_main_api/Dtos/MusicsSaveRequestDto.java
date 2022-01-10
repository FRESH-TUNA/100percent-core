package com.main.koko_main_api.Dtos;

import com.main.koko_main_api.Models.Musics;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */
@NoArgsConstructor
@Getter
public class MusicsSaveRequestDto {
    private String title;

    @Builder
    public MusicsSaveRequestDto(String title) {
        this.title = title;
    }

    public Musics toEntity() {
        return Musics.builder()
                .title(title)
                .build();
    }
}
