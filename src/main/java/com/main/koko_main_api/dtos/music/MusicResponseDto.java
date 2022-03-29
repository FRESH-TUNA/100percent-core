package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.dtos.music.bpm.MusicAlbumDto;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsDto;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MusicResponseDto extends RepresentationModel<MusicResponseDto> {
    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicAlbumDto album;
    private List<MusicPatternsDto> playables;

    public MusicResponseDto(MusicDto m)  {
        id = m.getId();
        title = m.getTitle();
        createdDate = m.getCreatedDate();
        modifiedDate = m.getModifiedDate();
        album = m.getAlbum();
        this.playables = m.getPlayables();
    }
}
