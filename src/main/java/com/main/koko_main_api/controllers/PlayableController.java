package com.main.koko_main_api.controllers;

import com.main.koko_main_api.assemblers.playable.PlayableDetailPayloadAssembler;
import com.main.koko_main_api.dtos.playable.PlayableDetailResponseEntityDto;
import com.main.koko_main_api.assemblers.playable.PlayableListPayloadAssembler;
import com.main.koko_main_api.dtos.playable.PlayableListResponseEntityDto;
import com.main.koko_main_api.payloads.playable.PlayableDetailPayload;
import com.main.koko_main_api.payloads.playable.PlayableSavePayload;

import com.main.koko_main_api.payloads.playable.PlayableListPayload;
import com.main.koko_main_api.services.PlayableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PlayableController {
    private final PagedResourcesAssembler<PlayableListResponseEntityDto> pageAssembler;
    private final PlayableListPayloadAssembler listAssembler;
    private final PlayableDetailPayloadAssembler detailAssembler;
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
    public PlayableDetailPayload save(@RequestBody PlayableSavePayload payload) {
        PlayableDetailResponseEntityDto response = playableService.save(payload);
        return detailAssembler.toModel(response);
    }

    @GetMapping("/main_api/v1/playables/{id}")
    public PlayableDetailPayload findById(@PathVariable Long id) {
        PlayableDetailResponseEntityDto response = playableService.findById(id);
        return detailAssembler.toModel(response);
    }

    @GetMapping("/main_api/v1/playables")
    public ResponseEntity<PagedModel<PlayableListPayload>> findAll(
            Pageable pageable,
            @RequestParam(value="play_type") String play_type) {
        Page<PlayableListResponseEntityDto> playables =
                playableService.findAll(pageable, play_type);
        return ResponseEntity.ok(pageAssembler.toModel(playables, listAssembler));
    }
}
