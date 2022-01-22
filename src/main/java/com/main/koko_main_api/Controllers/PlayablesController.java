package com.main.koko_main_api.Controllers;

import com.main.koko_main_api.Dtos.PlayablesResponseDto;
import com.main.koko_main_api.Dtos.PlayablesSaveRequestDto;
import com.main.koko_main_api.Services.PlayablesService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PlayablesController {
    private final PlayablesService playablesService;

    //save
    //bpm의 경우 bpm의정보를 리스트로 미리 받는다.
    //bpm을 제외한 다른 리소스는 URI (id) 로 받는다. (이미 생성되어있어야 한다)
    //level: 1
    //bpms: []
    //나머지는 링크로 받는다.
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/main_api/v1/playables")
    public PlayablesResponseDto save(@RequestBody PlayablesSaveRequestDto dto) {
        return playablesService.save(dto);
    }
}
