package com.main.koko_main_api.dtos.playable;

import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.dtos.playable.bpm.PlayableBpmResponseEntityDto;
import com.main.koko_main_api.dtos.playable.music.MusicResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class PlayableDetailResponseEntityDto {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicResponseDto music;
    private List<PlayableBpmResponseEntityDto> bpms;

    public PlayableDetailResponseEntityDto(Playable p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = new MusicResponseDto(p.getMusic());
        bpms = p.getBpms().stream()
                .map(bpm -> new PlayableBpmResponseEntityDto(bpm))
                .collect(Collectors.toList());
    }
}
