package com.main.koko_main_api.dtos.playable;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class PlayableDetailPayload extends RepresentationModel<PlayableDetailPayload> {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private PlayableMusicEntityDto music;

    public PlayableDetailPayload(PlayableDetailResponseEntityDto p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = p.getMusic();
    }
}
