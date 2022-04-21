package com.main.koko_main_api.assemblers.album;

import com.main.koko_main_api.controllers.AlbumsController;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.dtos.album.AlbumResponseDto;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlbumAssembler implements
        RepresentationModelAssembler<Album, AlbumResponseDto> {

    @Override
    public AlbumResponseDto toModel(Album m) {
        AlbumResponseDto res = new AlbumResponseDto(m);

        // add self link
        res.add(linkTo(methodOn(AlbumsController.class).findById(res.getId())).withSelfRel());
        return res;
    }
}
