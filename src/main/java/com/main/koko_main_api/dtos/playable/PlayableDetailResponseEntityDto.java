package com.main.koko_main_api.dtos.playable;

import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.dtos.music.MusicBpmEntityDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlayableDetailResponseEntityDto {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private PlayableMusicEntityDto music;

    public PlayableDetailResponseEntityDto(Playable p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = new PlayableMusicEntityDto(p.getMusic());
    }
}
