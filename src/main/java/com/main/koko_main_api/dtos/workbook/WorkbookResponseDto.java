package com.main.koko_main_api.dtos.workbook;

import com.main.koko_main_api.domains.Workbook;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WorkbookResponseDto extends RepresentationModel<WorkbookResponseDto> {
    private String title;
    private String description;
    private String play_type;
    private List<WorkbookPatternsResponseDto> patterns;

    public WorkbookResponseDto(Workbook w, List<WorkbookPatternsResponseDto> patterns) {
        this.title = w.getTitle();
        this.description = w.getDescription();
        this.play_type = w.getPlayType().getTitle();
        this.patterns = patterns;
    }
}
