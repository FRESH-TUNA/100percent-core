package com.main.koko_main_api.dtos.playable;

import com.main.koko_main_api.domains.Playable;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlayableListResponseEntityDto {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private PlayableMusicEntityDto music;

    public PlayableListResponseEntityDto(Playable p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = new PlayableMusicEntityDto(p.getMusic());
    }
}
