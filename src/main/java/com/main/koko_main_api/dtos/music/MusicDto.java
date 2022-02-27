package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.bpm.MusicAlbumDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MusicDto {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicAlbumDto album;
    private List<MusicPlayablesDto> playables;

    public MusicDto(Music m, List<MusicPlayablesDto> playables)  {
        id = m.getId();
        createdDate = m.getCreatedDate();
        modifiedDate = m.getModifiedDate();
        album = new MusicAlbumDto(m.getAlbum());
        this.playables = playables;
    }
}
