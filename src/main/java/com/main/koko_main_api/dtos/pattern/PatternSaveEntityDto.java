package com.main.koko_main_api.dtos.pattern;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternSaveEntityDto {
    private Integer level;
    private Music music;

    public Pattern toEntity() {
        return Pattern.builder()
                .level(level)
                .music(music).build();
    }
}
