package com.main.koko_main_api.services;

import com.main.koko_main_api.domainDtos.album.AlbumsResponseDto;
import com.main.koko_main_api.domainDtos.album.AlbumsSaveRequestDto;
import com.main.koko_main_api.domainDtos.album.AlbumsUpdateRequestDto;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.repositories.album.AlbumCustomRepository;
import com.main.koko_main_api.repositories.album.AlbumsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumsService {
    //private final AlbumsRepository albumsRepository;
    private final AlbumCustomRepository albumsRepository;

    @Transactional
    public AlbumsResponseDto save(AlbumsSaveRequestDto requestDto) {
        return this.findById(
            albumsRepository.save(requestDto.toEntity()).getId());
    }

    // transaction이 끝나는순간 변경된 부분을 반영한다.
    @Transactional
    public AlbumsResponseDto update(Long id, AlbumsUpdateRequestDto dto) {
        Album album = albumsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        album.update(dto.getTitle());
        return this.findById(id);
    }

    @Transactional
    public Long delete(Long id) {
        albumsRepository.deleteById(id);
        return id;
    }

    public AlbumsResponseDto findById(Long id) {
        Album album = albumsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        return new AlbumsResponseDto(album);
    }

    public List<AlbumsResponseDto> findAll() {
        return albumsRepository.findAll().parallelStream().map(
                (x) -> {return new AlbumsResponseDto(x);})
                .collect(Collectors.toList());
    }
}
