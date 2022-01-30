package com.main.koko_main_api.dtos.playable;

import com.main.koko_main_api.controllers.PlayableController;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.dtos.playable.bpm.BpmsResponseDto;
import com.main.koko_main_api.dtos.playable.music.MusicResponseDto;
import com.main.koko_main_api.repositories.MusicsRepository;
import lombok.Getter;

import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class PlayableDetailResponse extends RepresentationModel<PlayableDetailResponse> {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MusicResponseDto music;
    private List<BpmsResponseDto> bpms;

    public PlayableDetailResponse(Playable p, RepositoryEntityLinks helper)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = new MusicResponseDto(p.getMusic());
        bpms = p.getBpms().stream()
                .map(bpm -> new BpmsResponseDto(bpm))
                .collect(Collectors.toList());

        addLinks(helper);
    }

    private void addLinks(RepositoryEntityLinks helper) {
        this.add(linkTo(methodOn(PlayableController.class
        ).findById(this.getId())).withSelfRel());
        this.add(helper.linkToItemResource(
                MusicsRepository.class, this.getMusic().getId()));
    }
}
