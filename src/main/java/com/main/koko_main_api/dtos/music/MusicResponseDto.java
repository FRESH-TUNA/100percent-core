package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.album.MusicAlbumResponseDto;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsResponseDto;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MusicResponseDto extends RepresentationModel<MusicResponseDto> {
    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicAlbumResponseDto album;
    private Integer min_bpm, max_bpm;
    private List<MusicPatternsResponseDto> patterns;


    public MusicResponseDto(Music m, List<MusicPatternsResponseDto> patterns)  {
        id = m.getId();
        title = m.getTitle();
        createdDate = m.getCreatedDate();
        modifiedDate = m.getModifiedDate();
        album = new MusicAlbumResponseDto(m.getAlbum());
        min_bpm = m.getMin_bpm(); max_bpm = m.getMax_bpm();
        this.patterns = patterns;
    }
}
