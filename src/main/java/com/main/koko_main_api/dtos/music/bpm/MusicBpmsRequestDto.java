package com.main.koko_main_api.dtos.music.bpm;

import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.Music;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MusicBpmsRequestDto {
    private Integer value;

    public Bpm toEntity(Music music) {
        return Bpm.builder().music(music).value(value).build();
    }
}
