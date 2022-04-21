package com.main.koko_main_api.assemblers.album;

import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.dtos.album.AlbumResponseDto;
import com.main.koko_main_api.repositories.album.AlbumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AlbumAssembler implements
        RepresentationModelAssembler<Album, AlbumResponseDto> {

    @Autowired
    private RepositoryEntityLinks linkHelper;

    @Override
    public AlbumResponseDto toModel(Album m) {
        AlbumResponseDto res = new AlbumResponseDto(m);

        // add self link
        res.add(linkHelper.linkToItemResource(AlbumRepository.class, res.getId()));

        return res;
    }
}
