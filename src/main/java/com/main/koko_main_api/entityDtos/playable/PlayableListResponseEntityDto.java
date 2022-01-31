package com.main.koko_main_api.entityDtos.playable;

import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.entityDtos.playable.bpm.BpmsResponseDto;
import com.main.koko_main_api.entityDtos.playable.music.MusicResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlayableListResponseEntityDto {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicResponseDto music;
    private List<BpmsResponseDto> bpms;

    public PlayableListResponseEntityDto(Playable p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = new MusicResponseDto(p.getMusic());
        bpms = p.getBpms().stream()
                .map(bpm -> new BpmsResponseDto(bpm))
                .collect(Collectors.toList());
    }
}
