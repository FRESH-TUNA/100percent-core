package com.main.koko_main_api.dtos.pattern;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.bpm.MusicBpmEntityDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PatternMusicEntityDto {
    private Long id;
    private String title;
    private List<MusicBpmEntityDto> bpms;

    public PatternMusicEntityDto(Music music) {
        this.id = music.getId();
        this.title = music.getTitle();
        this.bpms = music.getBpms().stream()
                .map(bpm -> new MusicBpmEntityDto(bpm))
                .collect(Collectors.toList());
    }
}
