package com.main.koko_main_api.Controllers;

import com.main.koko_main_api.Dtos.AlbumsResponseDto;
import com.main.koko_main_api.Dtos.AlbumsSaveRequestDto;
import com.main.koko_main_api.Dtos.AlbumsUpdateRequestDto;
import com.main.koko_main_api.Services.AlbumsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlbumsController {
    private final AlbumsService albumsService;

    @GetMapping("/main_api/v1/albums")
    public List<AlbumsResponseDto> findAll() {
        return albumsService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/main_api/v1/albums")
    public AlbumsResponseDto save(@RequestBody AlbumsSaveRequestDto requestDto) {
        return albumsService.save(requestDto);
    }

    @GetMapping("/main_api/v1/albums/{id}")
    public AlbumsResponseDto findById(@PathVariable Long id) {
        return albumsService.findById(id);
    }

    @PutMapping("/main_api/v1/albums/{id}")
    public AlbumsResponseDto update(@PathVariable Long id, @RequestBody AlbumsUpdateRequestDto requestDto) {
        return albumsService.update(id, requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/main_api/v1/albums/{id}")
    public Long delete(@PathVariable Long id) {
        return albumsService.delete(id);
    }
}
