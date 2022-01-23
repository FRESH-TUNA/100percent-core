package com.main.koko_main_api.Controllers;

import com.main.koko_main_api.Dtos.PlayablesResponseDto;
import com.main.koko_main_api.Dtos.PlayablesSaveRequestDto;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.main.koko_main_api.Repositories.MusicsRepository;
import com.main.koko_main_api.Services.PlayablesService;
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
    //https://niks36.medium.com/spring-hateoas-part-1-f4609db195ab
    //save
    //bpm의 경우 bpm의정보를 리스트로 미리 받는다.
    //bpm을 제외한 다른 리소스는 URI (id) 로 받는다. (이미 생성되어있어야 한다)
    //level: 1
    //bpms: []
    //나머지는 링크로 받는다.
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/main_api/v1/playables")
    public PlayablesResponseDto save(@RequestBody PlayablesSaveRequestDto dto) {
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
