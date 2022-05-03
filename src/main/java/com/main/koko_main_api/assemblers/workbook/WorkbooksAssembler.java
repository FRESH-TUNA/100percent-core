package com.main.koko_main_api.assemblers.workbook;


import com.main.koko_main_api.controllers.AlbumController;
import com.main.koko_main_api.controllers.MusicController;
import com.main.koko_main_api.controllers.PatternController;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.Workbook;
import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsResponseDto;
import com.main.koko_main_api.dtos.workbook.WorkbookResponseDto;
import com.main.koko_main_api.dtos.workbook.WorkbooksResponseDto;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/*
 * RepresentationModelAssemblerSupport 는 RepresentationModelAssembler 의 구현체
 * self.link들이 이미잘탑재 되어있다.
 * 여기서는 그냥 수동으로 만들었다.
 * model메소드를 직접 구현한다?!
 */
@Component
public class WorkbooksAssembler implements
        RepresentationModelAssembler<Workbook, WorkbooksResponseDto> {

    @Override
    public WorkbooksResponseDto toModel(Workbook w) {
        WorkbooksResponseDto res = new WorkbooksResponseDto(w);

        // add self link
//        res.add(linkTo(methodOn(MusicController.class).findById(m.getId())).withSelfRel());
//
//        // add album link
//        res.add(linkTo(methodOn(AlbumController.class).findById(m.getAlbum().getId())).withRel("album"));

        return res;
    }
}