package com.main.koko_main_api.dtos.pattern;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
public class PatternDetailPayload extends RepresentationModel<PatternDetailPayload> {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private PatternMusicEntityDto music;

    public PatternDetailPayload(PatternDetailResponseEntityDto p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = p.getMusic();
    }
}
