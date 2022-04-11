package com.main.koko_main_api.controllers.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.MusicFindAllRequestParams;
import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.dtos.music.MusicRequestDto;

import com.main.koko_main_api.services.music.MusicFilterFindAllService;
import com.main.koko_main_api.services.music.MusicService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MusicController {
    private final MusicService musicService;
    private final MusicFilterFindAllService filterFindAllService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/main_api/v1/musics")
    public MusicResponseDto save(@RequestBody MusicRequestDto dto) {
        return musicService.create_or_update(dto);
    }

    /*
     * 개발중
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path="/main_api/v1/musics/{id}")
    public MusicResponseDto findById(Long id) {
        return new MusicResponseDto(new Music());
    }

    /*
     * 개발중
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="/main_api/vi/musics")
    public PagedModel<MusicResponseDto> findAll
        (@RequestParam MusicFindAllRequestParams params, Pageable pageable) {

        String mode = params.getMode();
        Long album_id = params.getAlbum(), play_type_id = params.getPlay_type();
        Long d_id = params.getDifficulty_type(); Integer level = params.getLevel();

        if(mode == null || mode.equals("db")) {
            if(album_id == null) return musicService.findAll(pageable, play_type_id);
            else return musicService.findAllByAlbum(pageable, play_type_id, album_id);
        }
        else if(mode.equals("difficulty"))
            return filterFindAllService.findAllByDifficulty(play_type_id, d_id);
        else
            return filterFindAllService.findAllByLevel(play_type_id, level);
    }
}

//        if(mode.equals("difficulty")) return difficultyFindAll(params);
//        if(mode.equals("level")) return levelFindAll(params);