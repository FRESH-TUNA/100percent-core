package com.main.koko_main_api.assemblers.album;

import com.main.koko_main_api.controllers.AlbumController;
import com.main.koko_main_api.controllers.MusicController;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.album.AlbumMusicsResponseDto;
import com.main.koko_main_api.dtos.album.AlbumResponseDto;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlbumAssembler implements
        RepresentationModelAssembler<Album, AlbumResponseDto> {

    @Override
    public AlbumResponseDto toModel(Album m) {
        AlbumResponseDto res = new AlbumResponseDto(m, musics(m.getMusics()));

        // add self link
        res.add(linkTo(methodOn(AlbumController.class).findById(m.getId())).withSelfRel());
        return res;
    }

    private List<AlbumMusicsResponseDto> musics(List<Music> musics) {
        return musics.stream().map(m -> {
            AlbumMusicsResponseDto dto = new AlbumMusicsResponseDto(m);
            dto.add(linkTo(methodOn(MusicController.class).findById(m.getId())).withSelfRel());
            return dto;
        }).collect(Collectors.toList());
    }
}