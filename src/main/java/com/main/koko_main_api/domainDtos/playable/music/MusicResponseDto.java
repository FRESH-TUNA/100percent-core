package com.main.koko_main_api.domainDtos.playable.music;

import com.main.koko_main_api.domains.Music;
import lombok.Getter;

@Getter
public class MusicResponseDto {
    private Long id;
    private String title;

    public MusicResponseDto(Music music) {
        this.id = music.getId();
        this.title = music.getTitle();
    }
}
