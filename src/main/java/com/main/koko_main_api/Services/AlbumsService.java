package com.main.koko_main_api.Services;

import com.main.koko_main_api.Dtos.AlbumsSaveRequestDto;
import com.main.koko_main_api.Repositories.AlbumsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AlbumsService {
    private final AlbumsRepository albumsRepository;

    @Transactional
    public Long save(AlbumsSaveRequestDto requestDto) {
        return albumsRepository.save(requestDto.toEntity()).getId();
    }
}
