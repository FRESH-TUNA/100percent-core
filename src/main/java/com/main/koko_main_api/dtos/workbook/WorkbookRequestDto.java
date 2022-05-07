package com.main.koko_main_api.dtos.workbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkbookRequestDto {
    private String title;
    private String description;
    private List<URI> patterns;
}
