package com.main.koko_main_api.Dtos;

import com.main.koko_main_api.Models.Bpm;
import com.main.koko_main_api.Models.Music;
import com.main.koko_main_api.Models.Playable;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PlayablesResponseDto extends RepresentationModel<PlayablesResponseDto> {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Music music;
    private List<Bpm> bpms;

    public PlayablesResponseDto(Playable p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = p.getMusic();
        bpms = p.getBpms();
    }
}
