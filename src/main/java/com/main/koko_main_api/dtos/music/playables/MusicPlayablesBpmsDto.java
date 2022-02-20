package com.main.koko_main_api.dtos.music.playables;

import com.main.koko_main_api.domains.Bpm;

public class MusicPlayablesBpmsDto {
    private Long id;
    private Integer value;

    public MusicPlayablesBpmsDto(Bpm entity) {
        this.id = entity.getId();
        this.value = entity.getValue();
    }
}
