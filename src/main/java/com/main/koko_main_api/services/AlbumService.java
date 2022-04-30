package com.main.koko_main_api.services;

import com.main.koko_main_api.assemblers.album.AlbumAssembler;
import com.main.koko_main_api.assemblers.album.AlbumsAssembler;
import com.main.koko_main_api.dtos.album.AlbumResponseDto;
import com.main.koko_main_api.dtos.album.AlbumsResponseDto;
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
public class AlbumService {
    private final AlbumRepository albumsRepository;
    private final AlbumsAssembler listAssembler;
    private final AlbumAssembler showAssembler;

    @Transactional
    public AlbumResponseDto save(AlbumRequestDto dto) {
        Album album = albumsRepository.save(dto.toEntity());
        return showAssembler.toModel(album);
    }

    @Transactional
    public AlbumResponseDto update(Long id, AlbumRequestDto dto) {
        Album album = albumsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        album.update(dto.toEntity());
        return showAssembler.toModel(album);
    }

    @Transactional
    public void delete(Long id) {
        albumsRepository.deleteById(id);
    }

    public AlbumResponseDto findById(Long id) {
        Album album = albumsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        return showAssembler.toModel(album);
    }

    public List<AlbumsResponseDto> findAll() {
        return albumsRepository.findAll().stream().map((a) -> listAssembler.toModel(a))
                .collect(Collectors.toList());
    }
}
