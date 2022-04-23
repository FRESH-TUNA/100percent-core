package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.album.AlbumResponseDto;
import com.main.koko_main_api.dtos.album.AlbumsResponseDto;
import com.main.koko_main_api.dtos.album.AlbumRequestDto;
import com.main.koko_main_api.services.AlbumsService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
public class AlbumController implements
        RepresentationModelProcessor<RepositoryLinksResource> {
    private final AlbumsService albumsService;

    @GetMapping
    public List<AlbumsResponseDto> findAll() {
        return albumsService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AlbumResponseDto save(@RequestBody AlbumRequestDto requestDto) {
        return albumsService.save(requestDto);
    }

    @GetMapping("/{id}")
    public AlbumResponseDto findById(@PathVariable Long id) {
        return albumsService.findById(id);
    }

    @PutMapping("/{id}")
    public AlbumResponseDto update(@PathVariable Long id, @RequestBody AlbumRequestDto requestDto) {
        return albumsService.update(id, requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        albumsService.delete(id);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(AlbumController.class).withRel("albums"));
        return model;
    }
}
