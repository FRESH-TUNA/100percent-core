package com.main.koko_main_api.controllers.Playables;

import com.main.koko_main_api.services.Playables.BpmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;


@RequiredArgsConstructor
@RepositoryRestController
public class BpmsController {
    private final BpmsService bpmsService;

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/playables/{id}/bpms")
//    public void save(@PathVariable Long playable_id, @RequestBody BpmsSaveDto dto) {
//        bpmsService.save(playable_id, dto);
//    }
}
