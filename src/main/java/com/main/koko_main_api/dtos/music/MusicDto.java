package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.bpm.MusicAlbumDto;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MusicDto {
    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicAlbumDto album;
    private List<MusicPatternsDto> playables;

    /*
     * for findById
     */
    public MusicDto(Music m) {
        id = m.getId();
        title = m.getTitle();
        createdDate = m.getCreatedDate();
        modifiedDate = m.getModifiedDate();
        album = new MusicAlbumDto(m.getAlbum());
        playables = new ArrayList<>();
    }

    public void addMusicPatternDto(MusicPatternsDto dto) {
        this.playables.add(dto);
    }
}
