package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.controllers.PlayableController;
import com.main.koko_main_api.controllers.music.MusicController;
import com.main.koko_main_api.domains.Music;

import com.main.koko_main_api.dtos.music.playables.MusicPlayablesDto;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/*
 * RepresentationModelAssemblerSupport 는 RepresentationModelAssembler 의 구현체
 * self.link들이 이미잘탑재 되어있다.
 * 여기서는 그냥 수동으로 만들었다.
 * model메소드를 직접 구현한다?!
 */
@Component
public class MusicDtoAssembler implements
        RepresentationModelAssembler<Music, MusicDto> {

    @Autowired
    private RepositoryEntityLinks linkHelper;

    @Override
    public MusicDto toModel(Music m) {
        MusicDto payload = new MusicDto(m);

        // add self links
        payload.add(linkTo(methodOn(MusicController.class
            ).findById(payload.getId())).withSelfRel());

        // add album links
        payload.add(linkHelper.linkToItemResource(
                AlbumRepository.class, payload.getAlbum().getId()));

        add_link_to_playables(payload.getPlayables());

        return payload;
    }

    private void add_link_to_playables(List<MusicPlayablesDto> dtos) {
        for(MusicPlayablesDto dto : dtos)
            dto.add(linkTo(methodOn(PlayableController.class
                ).findById(dto.getId())).withSelfRel());
    }
}