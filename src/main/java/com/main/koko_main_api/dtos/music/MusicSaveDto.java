package com.main.koko_main_api.dtos.music;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Composer;
import com.main.koko_main_api.domains.Music;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MusicSaveDto {
    private String title;
    private Album album;
    private List<Composer> composers;

    public Music toEntity() {
        return Music.builder()
                .title(title)
                .album(album).build();
    }

    @Builder
    public MusicSaveDto(String title, Album album, List<Composer> composers) {
        this.title = title;
        this.album = album;
        this.composers = composers;
    }
}
