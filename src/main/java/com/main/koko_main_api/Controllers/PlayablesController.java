package com.main.koko_main_api.Controllers;

import com.main.koko_main_api.Dtos.PlayablesResponseDto;
import com.main.koko_main_api.Dtos.PlayablesSaveDto;
import com.main.koko_main_api.Services.PlayablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// https://blog.neonkid.xyz/222

@RequiredArgsConstructor
@RepositoryRestController
public class PlayablesController {
    private final PlayablesService playablesService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/playables")
    public PlayablesResponseDto save(@RequestBody PlayablesSaveDto dto) {
        PlayablesResponseDto responseDto = playablesService.save(dto);
        return responseDto;
    }
}
