package com.main.koko_main_api.assemblers;

import com.main.koko_main_api.controllers.WorkbookController;
import com.main.koko_main_api.domains.Workbook;
import com.main.koko_main_api.dtos.workbook.WorkbooksResponseDto;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WorkbooksAssembler implements
        RepresentationModelAssembler<Workbook, WorkbooksResponseDto> {

    @Override
    public WorkbooksResponseDto toModel(Workbook w) {
        WorkbooksResponseDto res = new WorkbooksResponseDto(w);

        //add self link
        res.add(linkTo(methodOn(WorkbookController.class).findById(w.getId())).withSelfRel());
        return res;
    }
}