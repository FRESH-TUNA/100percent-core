package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.dtos.music.playables.MusicPlayablesBpmsResponseEntityDto;
import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.DifficultyType;
import com.main.koko_main_api.domains.PlayType;
import com.main.koko_main_api.domains.Playable;

import java.util.ArrayList;
import java.util.List;


public class MusicPlayablesResponseEntityDto {
    private Long id;
    private Integer level;
    private DifficultyType difficultyType;
    private PlayType playType;
    private List<MusicPlayablesBpmsResponseEntityDto> bpms = new ArrayList();

    public MusicPlayablesResponseEntityDto(Playable p) {
        this.id = p.getId();
        this.level = p.getLevel();
        this.difficultyType = p.getDifficultyType();
        this.playType = p.getPlayType();

        for(Bpm bpm : p.getBpms())
            bpms.add(new MusicPlayablesBpmsResponseEntityDto(bpm));
    }
}
