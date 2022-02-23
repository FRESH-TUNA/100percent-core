package com.main.koko_main_api.payloads.playable;

import com.main.koko_main_api.dtos.playable.PlayableDetailResponseEntityDto;
import com.main.koko_main_api.dtos.music.MusicBpmEntityDto;
import com.main.koko_main_api.dtos.playable.PlayableMusicEntityDto;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

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
