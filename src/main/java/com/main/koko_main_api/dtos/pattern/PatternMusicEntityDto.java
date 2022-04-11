package com.main.koko_main_api.dtos.pattern;

import com.main.koko_main_api.domains.Music;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PatternMusicEntityDto {
    private Long id;
    private String title;

    public PatternMusicEntityDto(Music music) {
        this.id = music.getId();
        this.title = music.getTitle();
    }
}
