package com.main.koko_main_api.controllers.music;

import com.main.koko_main_api.dtos.pattern.PatternRequestDto;
import com.main.koko_main_api.services.music.MusicPatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/musics")
public class MusicPatternController {
    private final MusicPatternService musicPatternService;

    @PostMapping("/{music_id}/patterns")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable Long music_id, PatternRequestDto dto) {
        musicPatternService.create(music_id, dto);
    }
}
