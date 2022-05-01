package com.main.koko_main_api.assemblers.workbook;

import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.dtos.RequestDeassembler;
import com.main.koko_main_api.dtos.workbook.WorkbookRequestDto;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkbookDeassembler
        implements RequestDeassembler<WorkbookRequestDto, Workbook> {
    @Autowired
    private PatternRepository patternRepository;

    @Override
    public Workbook toEntity(WorkbookRequestDto dto) {
        String title = dto.getTitle();
        String description = dto.getDescription();

        Workbook workbook = Workbook.builder().title(title)
                .description(description).build();

        List<WorkbookPattern> patterns = workbookPatterns(patterns(dto.getPatterns()), workbook);

        workbook.add_patterns(patterns);
        return workbook;
    }

    private List<Pattern> patterns(List<URI> patterns) {
        return patterns.stream()
                .map(p -> patternRepository.getById(convertURItoID(p)))
                .collect(Collectors.toList());
    }

    private List<WorkbookPattern> workbookPatterns(List<Pattern> patterns, Workbook workbook) {
        return patterns.stream()
                .map(p -> WorkbookPattern.builder().pattern(p).workbook(workbook).build())
                .collect(Collectors.toList());
    }

    private Long convertURItoID(URI uri) {
        String[] data = uri.toString().split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
