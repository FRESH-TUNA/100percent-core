package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.album.AlbumResponseDto;
import com.main.koko_main_api.dtos.album.AlbumRequestDto;
import com.main.koko_main_api.services.AlbumsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlbumsController {
    private final AlbumsService albumsService;

    @GetMapping("/main_api/v1/albums")
    public List<AlbumResponseDto> findAll() {
        return albumsService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/main_api/v1/albums")
    public AlbumResponseDto save(@RequestBody AlbumRequestDto requestDto) {
        return albumsService.save(requestDto);
    }

    @GetMapping("/main_api/v1/albums/{id}")
    public AlbumResponseDto findById(@PathVariable Long id) {
        return albumsService.findById(id);
    }

    @PutMapping("/main_api/v1/albums/{id}")
    public AlbumResponseDto update(@PathVariable Long id, @RequestBody AlbumRequestDto requestDto) {
        return albumsService.update(id, requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/main_api/v1/albums/{id}")
    public void delete(@PathVariable Long id) {
        albumsService.delete(id);
    }
}
