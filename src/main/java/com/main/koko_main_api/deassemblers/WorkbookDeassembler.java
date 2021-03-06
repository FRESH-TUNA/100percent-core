package com.main.koko_main_api.deassemblers;

import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.dtos.workbook.WorkbookRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WorkbookDeassembler {

    public Workbook toEntity(WorkbookRequestDto dto, List<Pattern> patterns) {
        String title = dto.getTitle();
        String description = dto.getDescription();
        PlayType playType = getPlayType(patterns);

        return Workbook.builder().title(title).playType(playType)
                .description(description).build();
    }


    private PlayType getPlayType(List<Pattern> patterns) {
        Set<PlayType> playTypes = patterns.stream().map(p -> p.getPlayType()).collect(Collectors.toSet());
        return (playTypes.size() > 1) ? null : patterns.get(0).getPlayType();
    }
}
