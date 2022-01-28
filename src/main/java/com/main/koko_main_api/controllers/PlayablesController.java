package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.playable.PlayablesResponseDto;
import com.main.koko_main_api.dtos.playable.PlayableSavePayload;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.main.koko_main_api.repositories.MusicsRepository;
import com.main.koko_main_api.services.PlayablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
public class PlayablesController {
    private final RepositoryEntityLinks entityLinks;
    private final PlayablesService playablesService;

    /*
     * autowired를 통해 다른 스프링에 등록된 bean(service)를 주입할수 있다.
     */
//    @Autowired
//    public PlayablesController(PlayablesService service) {
//        this.playablesService = service;
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/main_api/v1/playables")
    public PlayablesResponseDto save(@RequestBody PlayableSavePayload dto) {
        PlayablesResponseDto response = playablesService.save(dto);

        response.add(linkTo(methodOn(PlayablesController.class
            ).findById(response.getId())).withSelfRel());
        response.add(entityLinks.linkToItemResource(
                MusicsRepository.class, response.getMusic().getId()));

        return response;
    }

    @GetMapping("/main_api/v1/playables/{id}")
    public ResponseEntity<PlayablesResponseDto> findById(@PathVariable Long id) {
        PlayablesResponseDto response = playablesService.findById(id);

        response.add(linkTo(methodOn(PlayablesController.class
            ).findById(id)).withSelfRel());
        response.add(entityLinks.linkToItemResource(
                MusicsRepository.class, response.getMusic().getId()));

        return ResponseEntity.ok(response);
    }
}
