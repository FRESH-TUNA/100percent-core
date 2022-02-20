package com.main.koko_main_api.dtos.music.playables;

import com.main.koko_main_api.domains.Bpm;

public class MusicPlayablesBpmsResponseEntityDto {
    private Long id;
    private Integer value;

    public MusicPlayablesBpmsResponseEntityDto(Bpm entity) {
        this.id = entity.getId();
        this.value = entity.getValue();
    }
}
