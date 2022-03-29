package com.main.koko_main_api.controllers.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.dtos.music.MusicDto;
import com.main.koko_main_api.dtos.music.MusicRequestDto;

import com.main.koko_main_api.services.music.MusicService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MusicController {
    private final MusicService musicService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/main_api/v1/musics")
    public MusicResponseDto save(@RequestBody MusicRequestDto payload) {
        return musicService.save(payload);
    }

    /*
     * 개발중
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path="/main_api/v1/musics/{id}")
    public MusicResponseDto findById(Long id) {
        return new MusicResponseDto(new MusicDto(new Music()));
    }
}
