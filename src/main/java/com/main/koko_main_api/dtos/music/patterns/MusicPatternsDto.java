package com.main.koko_main_api.dtos.music.patterns;

import com.main.koko_main_api.domains.DifficultyType;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.PlayType;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class MusicPatternsDto extends RepresentationModel<MusicPatternsDto> {
    private Long id;
    private Integer level;
    private DifficultyType difficultyType;
    private PlayType playType;

    public MusicPatternsDto(Pattern p) {
        this.id = p.getId();
        this.level = p.getLevel();
        this.difficultyType = p.getDifficultyType();
        this.playType = p.getPlayType();
    }
}
