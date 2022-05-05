package com.main.koko_main_api.dtos.pattern;

import com.main.koko_main_api.domains.Pattern;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;

@Getter
public class PatternResponseDto extends RepresentationModel<PatternResponseDto> {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private PatternMusicResponseDto music;

    public PatternResponseDto(Pattern p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = new PatternMusicResponseDto(p.getMusic());
    }
}
