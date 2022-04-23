package com.main.koko_main_api.assemblers.album;

import com.main.koko_main_api.controllers.AlbumController;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.dtos.album.AlbumsResponseDto;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlbumsAssembler implements
        RepresentationModelAssembler<Album, AlbumsResponseDto> {

    @Override
    public AlbumsResponseDto toModel(Album m) {
        AlbumsResponseDto res = new AlbumsResponseDto(m);

        // add self link
        res.add(linkTo(methodOn(AlbumController.class).findById(m.getId())).withSelfRel());
        return res;
    }
}
