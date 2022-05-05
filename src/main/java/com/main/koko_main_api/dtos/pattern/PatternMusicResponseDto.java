package com.main.koko_main_api.dtos.pattern;

import com.main.koko_main_api.domains.Music;
import lombok.Getter;

@Getter
public class PatternMusicResponseDto {
    private Long id;
    private String title;
    private String album;

    public PatternMusicResponseDto(Music music) {
        this.id = music.getId();
        this.title = music.getTitle();
        this.album = music.getAlbum().getTitle();
    }
}
