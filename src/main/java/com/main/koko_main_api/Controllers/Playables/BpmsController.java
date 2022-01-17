package com.main.koko_main_api.Controllers.Playables;

import com.main.koko_main_api.Dtos.BpmsResponseDto;
import com.main.koko_main_api.Dtos.BpmsSaveDto;
import com.main.koko_main_api.Services.Playables.BpmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RepositoryRestController
public class BpmsController {
    private final BpmsService bpmsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/playables/{id}/bpms")
    public void save(@PathVariable Long playable_id, @RequestBody BpmsSaveDto dto) {
        bpmsService.save(playable_id, dto);
    }
}
