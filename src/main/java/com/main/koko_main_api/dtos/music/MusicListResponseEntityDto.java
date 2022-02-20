package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Music;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MusicListResponseEntityDto {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicAlbumResponseEntityDto album;
    private List<MusicPlayablesResponseEntityDto> playables;

    public MusicListResponseEntityDto(Music m, List<MusicPlayablesResponseEntityDto> playables)  {
        id = m.getId();
        createdDate = m.getCreatedDate();
        modifiedDate = m.getModifiedDate();
        album = new MusicAlbumResponseEntityDto(m.getAlbum());
        this.playables = playables;
    }
}
