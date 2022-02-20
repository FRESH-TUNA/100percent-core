package com.main.koko_main_api.payloads.playable;

import com.main.koko_main_api.dtos.playable.PlayableDetailResponseEntityDto;
import com.main.koko_main_api.dtos.playable.bpm.PlayableBpmResponseEntityDto;
import com.main.koko_main_api.dtos.playable.music.MusicResponseDto;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class PlayableDetailResponsePayload extends RepresentationModel<PlayableDetailResponsePayload> {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicResponseDto music;
    private List<PlayableBpmResponseEntityDto> bpms;

    public PlayableDetailResponsePayload(PlayableDetailResponseEntityDto p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = p.getMusic();
        bpms = p.getBpms();
    }
}
