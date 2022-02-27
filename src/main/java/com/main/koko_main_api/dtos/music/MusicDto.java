package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.bpm.MusicAlbumDto;
import com.main.koko_main_api.dtos.music.playables.MusicPlayablesDto;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MusicDto extends RepresentationModel<MusicDto> {
    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicAlbumDto album;
    private List<MusicPlayablesDto> playables;

    public MusicDto(Music m)  {
        id = m.getId();
        title = m.getTitle();
        createdDate = m.getCreatedDate();
        modifiedDate = m.getModifiedDate();
        album = new MusicAlbumDto(m.getAlbum());
        playables = m.getPlayables().stream()
                .map(p -> new MusicPlayablesDto(p))
                .collect(Collectors.toList());
    }
}
