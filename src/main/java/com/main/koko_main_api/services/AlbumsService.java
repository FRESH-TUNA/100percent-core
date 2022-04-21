package com.main.koko_main_api.services;

import com.main.koko_main_api.assemblers.album.AlbumAssembler;
import com.main.koko_main_api.dtos.album.AlbumResponseDto;
import com.main.koko_main_api.dtos.album.AlbumRequestDto;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumsService {
    private final AlbumRepository albumsRepository;
    private final AlbumAssembler assembler;

    @Transactional
    public AlbumResponseDto save(AlbumRequestDto dto) {
        Album album = albumsRepository.save(dto.toEntity());
        return assembler.toModel(album);
    }

    // transaction이 끝나는순간 변경된 부분을 반영한다.
    @Transactional
    public AlbumResponseDto update(Long id, AlbumRequestDto dto) {
        return assembler.toModel(albumsRepository.save(dto.toEntity(id)));
    }

    @Transactional
    public void delete(Long id) {
        albumsRepository.deleteById(id);
    }

    public AlbumResponseDto findById(Long id) {
        Album album = albumsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        return assembler.toModel(album);
    }

    public List<AlbumResponseDto> findAll() {
        return albumsRepository.findAll().stream().map((a) -> assembler.toModel(a))
                .collect(Collectors.toList());
    }
}
