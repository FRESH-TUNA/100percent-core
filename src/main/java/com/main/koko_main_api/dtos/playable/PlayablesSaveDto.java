package com.main.koko_main_api.dtos.playable;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Playable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayablesSaveDto {
    private Integer level;
    private Music music;

    public Playable toEntity() {
        return Playable.builder()
                .level(level)
                .music(music).build();
    }
}
