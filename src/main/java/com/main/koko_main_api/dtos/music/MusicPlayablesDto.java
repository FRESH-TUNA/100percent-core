package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.dtos.music.playables.MusicPlayablesBpmsDto;
import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.DifficultyType;
import com.main.koko_main_api.domains.PlayType;
import com.main.koko_main_api.domains.Playable;

import java.util.ArrayList;
import java.util.List;


public class MusicPlayablesDto {
    private Long id;
    private Integer level;
    private DifficultyType difficultyType;
    private PlayType playType;
    private List<MusicPlayablesBpmsDto> bpms = new ArrayList();

    public MusicPlayablesDto(Playable p) {
        this.id = p.getId();
        this.level = p.getLevel();
        this.difficultyType = p.getDifficultyType();
        this.playType = p.getPlayType();

        for(Bpm bpm : p.getBpms())
            bpms.add(new MusicPlayablesBpmsDto(bpm));
    }
}
