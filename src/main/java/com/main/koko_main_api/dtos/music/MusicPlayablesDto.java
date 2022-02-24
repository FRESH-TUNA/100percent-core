package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.DifficultyType;
import com.main.koko_main_api.domains.PlayType;
import com.main.koko_main_api.domains.Playable;
import lombok.Getter;

@Getter
public class MusicPlayablesDto {
    private Long id;
    private Integer level;
    private DifficultyType difficultyType;
    private PlayType playType;


    public MusicPlayablesDto(Playable p) {
        this.id = p.getId();
        this.level = p.getLevel();
        this.difficultyType = p.getDifficultyType();
        this.playType = p.getPlayType();
    }
}
