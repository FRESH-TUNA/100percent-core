package com.main.koko_main_api.assemblers;

import com.main.koko_main_api.controllers.MusicController;
import com.main.koko_main_api.controllers.PatternController;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.pattern.PatternResponseDto;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/*
 * RepresentationModelAssemblerSupport 는 RepresentationModelAssembler 의 구현체
 * self.link들이 이미잘탑재 되어있다.
 * 여기서는 그냥 수동으로 만들었다.
 * model메소드를 직접 구현한다?!
 */
@Component
public class PatternAssembler implements
        RepresentationModelAssembler<Pattern, PatternResponseDto> {

    @Override
    public PatternResponseDto toModel(Pattern p) {
        PatternResponseDto payload = new PatternResponseDto(p);

        /* add link */
        payload.add(linkTo(methodOn(PatternController.class).findById(payload.getId())).withSelfRel());
        return payload;
    }
}