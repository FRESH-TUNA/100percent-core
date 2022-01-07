package com.main.koko_main_api.Controllers;

import com.main.koko_main_api.Dtos.AlbumsSaveRequestDto;
import com.main.koko_main_api.Services.AlbumsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AlbumsController {
    private final AlbumsService albumsService;

    @PostMapping("/api/v1/albums")
    public Long save(@RequestBody AlbumsSaveRequestDto requestDto) {
        return albumsService.save(requestDto);
    }
}
