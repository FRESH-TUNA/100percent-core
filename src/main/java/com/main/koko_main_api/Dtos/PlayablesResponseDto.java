package com.main.koko_main_api.Dtos;

import com.main.koko_main_api.Models.Bpm;
import com.main.koko_main_api.Models.Music;
import com.main.koko_main_api.Models.Playable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class PlayablesResponseDto {
    private Long id;
    private Integer level;
    private Set<Bpm> bpms;
    private Music music;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PlayablesResponseDto(Playable entity) {
        this.id = entity.getId();
        this.level = entity.getLevel();
        this.bpms = entity.getBpms();
        this.music = entity.getMusic();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
