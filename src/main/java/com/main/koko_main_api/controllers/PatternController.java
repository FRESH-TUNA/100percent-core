package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.pattern.PatternDetailPayloadAssembler;
import com.main.koko_main_api.dtos.pattern.PatternDetailResponseEntityDto;
import com.main.koko_main_api.dtos.pattern.PatternListPayloadAssembler;
import com.main.koko_main_api.dtos.pattern.PatternListResponseEntityDto;
import com.main.koko_main_api.dtos.pattern.PatternDetailPayload;
import com.main.koko_main_api.dtos.pattern.PatternSavePayload;

import com.main.koko_main_api.services.PatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PatternController {
    private final PagedResourcesAssembler<PatternListResponseEntityDto> pageAssembler;
    private final PatternListPayloadAssembler listAssembler;
    private final PatternDetailPayloadAssembler detailAssembler;
    private final PatternService patternService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/main_api/v1/playables")
    public PatternDetailPayload save(@RequestBody PatternSavePayload payload) {
        PatternDetailResponseEntityDto response = patternService.save(payload);
        return detailAssembler.toModel(response);
    }

    @GetMapping("/main_api/v1/playables/{id}")
    public PatternDetailPayload findById(@PathVariable Long id) {
        PatternDetailResponseEntityDto response = patternService.findById(id);
        return detailAssembler.toModel(response);
    }
}
