package com.main.koko_main_api.dtos.workbook;

import com.main.koko_main_api.domains.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@RequiredArgsConstructor
public class WorkbookPatternsResponseDto extends RepresentationModel<WorkbookPatternsResponseDto> {
    private String music;
    private Integer level;
    private String difficultyType;
    private String playType;

    public WorkbookPatternsResponseDto(Pattern p) {
        this.music = p.getMusic().getTitle();
        level = p.getLevel();
        difficultyType = p.getDifficultyType().getName();
        playType = p.getPlayType().getTitle();
    }
}
