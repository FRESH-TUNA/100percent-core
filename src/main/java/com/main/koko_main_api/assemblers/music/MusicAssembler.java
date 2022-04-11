package com.main.koko_main_api.assemblers.music;

import com.main.koko_main_api.controllers.PatternController;
import com.main.koko_main_api.controllers.music.MusicController;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsResponseDto;
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
public class MusicAssembler implements
        RepresentationModelAssembler<Music, MusicResponseDto> {

    @Autowired
    private RepositoryEntityLinks linkHelper;

    @Override
    public MusicResponseDto toModel(Music m) {
        MusicResponseDto res = new MusicResponseDto(m);

        // add self link
        res.add(linkTo(methodOn(MusicController.class
            ).findById(res.getId())).withSelfRel());

        // add album link
        res.add(linkHelper.linkToItemResource(
                AlbumRepository.class, res.getAlbum().getId()));

        add_link_to_playables(res.getPatterns());

        return res;
    }

    private void add_link_to_playables(List<MusicPatternsResponseDto> dtos) {
        for(MusicPatternsResponseDto dto : dtos)
            dto.add(linkTo(methodOn(PatternController.class
                ).findById(dto.getId())).withSelfRel());
    }
}