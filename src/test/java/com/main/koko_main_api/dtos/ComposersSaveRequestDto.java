package com.main.koko_main_api.dtos;

import com.main.koko_main_api.domains.Composer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */
@NoArgsConstructor
@Getter
public class ComposersSaveRequestDto {
    private String name;

    @Builder
    public ComposersSaveRequestDto(String name) {
        this.name = name;
    }

    public Composer toEntity() {
        return Composer.builder()
                .name(name)
                .build();
    }
}
