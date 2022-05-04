package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.workbook.WorkbookRequestDto;
import com.main.koko_main_api.dtos.workbook.WorkbookResponseDto;
import com.main.koko_main_api.dtos.workbook.WorkbooksResponseDto;
import com.main.koko_main_api.services.workbook.WorkbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workbooks")
public class WorkbookController implements RepresentationModelProcessor<RepositoryLinksResource> {
    private final WorkbookService workbookService;

    @GetMapping
    public PagedModel<WorkbooksResponseDto> findAll(Pageable pageable, @RequestParam Long play_type_id) {
        if(play_type_id != null)
            return workbookService.findAll(pageable);
        else
            return workbookService.findAll(pageable, play_type_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public WorkbookResponseDto save(@RequestBody WorkbookRequestDto requestDto) {
        return workbookService.create(requestDto);
    }

    @GetMapping("/{id}")
    public WorkbookResponseDto findById(@PathVariable Long id) {
        return workbookService.findById(id);
    }

    @PutMapping("/{id}")
    public WorkbookResponseDto update(@PathVariable Long id, @RequestBody WorkbookRequestDto requestDto) {
        return workbookService.update(id, requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workbookService.delete(id);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(AlbumController.class).withRel("workbooks"));
        return model;
    }
}

