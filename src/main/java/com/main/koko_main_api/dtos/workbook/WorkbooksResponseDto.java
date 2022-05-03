package com.main.koko_main_api.dtos.workbook;

import com.main.koko_main_api.domains.Workbook;
import org.springframework.hateoas.RepresentationModel;

public class WorkbooksResponseDto extends RepresentationModel<WorkbooksResponseDto> {
    private String title;

    public WorkbooksResponseDto(Workbook workbook) {

        this.title = workbook.getTitle();
    }
}
