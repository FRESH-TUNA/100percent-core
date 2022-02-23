package com.main.koko_main_api.dtos.playable;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.MusicBpmEntityDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlayableMusicEntityDto {
    private Long id;
    private String title;
    private List<MusicBpmEntityDto> bpms;

    public PlayableMusicEntityDto(Music music) {
        this.id = music.getId();
        this.title = music.getTitle();
        this.bpms = music.getBpms().stream()
                .map(bpm -> new MusicBpmEntityDto(bpm))
                .collect(Collectors.toList());
    }
}
