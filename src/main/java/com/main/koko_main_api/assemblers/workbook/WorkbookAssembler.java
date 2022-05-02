package com.main.koko_main_api.assemblers.workbook;

import com.main.koko_main_api.controllers.PatternController;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.Workbook;

import com.main.koko_main_api.dtos.workbook.WorkbookPatternsResponseDto;
import com.main.koko_main_api.dtos.workbook.WorkbookResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WorkbookAssembler {
    public WorkbookResponseDto toModel(Workbook w, List<Pattern> patterns) {
        WorkbookResponseDto res = new WorkbookResponseDto(w, patterns(patterns));

        // add self link
//        res.add(linkTo(methodOn(MusicController.class).findById(m.getId())).withSelfRel());
//
//        // add album link
//        res.add(linkTo(methodOn(AlbumController.class).findById(m.getAlbum().getId())).withRel("album"));

        return res;
    }


    private List<WorkbookPatternsResponseDto> patterns(List<Pattern> patterns) {
        return patterns.stream().map(p -> {
            WorkbookPatternsResponseDto dto = new WorkbookPatternsResponseDto(p);
            dto.add(linkTo(methodOn(PatternController.class).findById(p.getId())).withSelfRel());
            return dto;
        }).collect(Collectors.toList());
    }
}