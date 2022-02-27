package com.main.koko_main_api.dtos.music.bpm;

import com.main.koko_main_api.domains.Bpm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MusicBpmEntityDto {
    private Long id;
    private Integer value;

    public MusicBpmEntityDto(Bpm entity) {
        this.id = entity.getId();
        this.value = entity.getValue();
    }
}
