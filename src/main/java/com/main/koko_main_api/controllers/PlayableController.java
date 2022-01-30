package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.playable.PlayableDetailResponse;
import com.main.koko_main_api.dtos.playable.PlayableListResponseAssembler;
import com.main.koko_main_api.dtos.playable.PlayableListResponseObject;
import com.main.koko_main_api.dtos.playable.PlayableSavePayload;

import com.main.koko_main_api.payloads.playable.PlayableListResponsePayload;
import com.main.koko_main_api.services.PlayableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PlayableController {
    private final PagedResourcesAssembler<PlayableListResponseObject> pageAssembler;
    private final PlayableListResponseAssembler assembler;
    private final PlayableService playableService;
    /*
     * autowired를 통해 다른 스프링에 등록된 bean(service)를 주입할수 있다.
     * 기본 생성자가 하나라면 생략 가능하다!
     */
//    @Autowired
//    public PlayablesController(PlayablesService service) {
//        this.playablesService = service;
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/main_api/v1/playables")
    public PlayableDetailResponse save(@RequestBody PlayableSavePayload dto) {
        PlayableDetailResponse response = playableService.save(dto);
        return response;
    }

    @GetMapping("/main_api/v1/playables/{id}")
    public ResponseEntity<PlayableDetailResponse> findById(@PathVariable Long id) {
        PlayableDetailResponse response = playableService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/main_api/v1/playables")
    public ResponseEntity<PagedModel<PlayableListResponsePayload>> findAll(Pageable pageable) {
        Page<PlayableListResponseObject> playables = playableService.findAll(pageable);
        return ResponseEntity.ok(pageAssembler.toModel(playables, assembler));
    }
}
