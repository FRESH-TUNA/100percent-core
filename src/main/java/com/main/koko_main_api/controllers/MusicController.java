package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.dtos.music.MusicRequestDto;

import com.main.koko_main_api.services.MusicService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/musics")
public class MusicController {
    private final MusicService musicService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MusicResponseDto save(@RequestBody MusicRequestDto dto) {
        return musicService.create(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path="/{id}")
    public MusicResponseDto update(@PathVariable Long id, @RequestBody MusicRequestDto dto) {
        return musicService.update(id, dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="/{id}")
    public MusicResponseDto findById(@PathVariable Long id) {
        return musicService.findById(id);
    }

    /*
     * 당분간 안씀
     */
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping
//    public PagedModel<MusicResponseDto> findAll
//        (@RequestParam MusicFindAllRequestParams params, Pageable pageable) {
//
//        String mode = params.getMode();
//        Long album_id = params.getAlbum(), play_type_id = params.getPlay_type();
//        Long d_id = params.getDifficulty_type(); Integer level = params.getLevel();
//
//        if(mode == null || mode.equals("db")) {
//            if(album_id == null) return musicService.findAll(pageable, play_type_id);
//            else return musicService.findAllByAlbum(pageable, play_type_id, album_id);
//        }
//        else if(mode.equals("difficulty"))
//            return musicService.findAllByDifficulty(play_type_id, d_id);
//        else
//            return musicService.findAllByLevel(play_type_id, level);
//    }
}
