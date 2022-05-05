package com.main.koko_main_api.dtos.pattern;

import com.main.koko_main_api.domains.Pattern;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;

@Getter
public class PatternResponseDto extends RepresentationModel<PatternResponseDto> {
    private Long id;
    private Integer level;
    private String difficulty_type;
    private String play_type;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PatternResponseDto(Pattern p)  {
        id = p.getId();
        level = p.getLevel();
        difficulty_type = p.getDifficultyType().getName();
        play_type = p.getPlayType().getTitle();

        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
    }
}
