package com.main.koko_main_api.dtos.music.patterns;

import com.main.koko_main_api.domains.DifficultyType;
import com.main.koko_main_api.domains.Pattern;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class MusicPatternsResponseDto extends RepresentationModel<MusicPatternsResponseDto> {
    private Long id;
    private Integer level;
    private Long difficultyType;

    public MusicPatternsResponseDto(Pattern p) {
        this.id = p.getId();
        this.level = p.getLevel();
        this.difficultyType = p.getDifficultyType().getId();
    }
}
