package com.main.koko_main_api.services;

import com.main.koko_main_api.dtos.playable.bpm.BpmsResponseDto;
import com.main.koko_main_api.dtos.playable.bpm.BpmsSaveDto;
import com.main.koko_main_api.dtos.playable.PlayablesResponseDto;
import com.main.koko_main_api.dtos.playable.PlayableSavePayload;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.repositories.BpmsRepository;
import com.main.koko_main_api.repositories.MusicsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PlayablesServiceTest {

    @Autowired
    private PlayablesService playablesService;

    @Autowired
    private MusicsRepository musicsRepository;

    @Autowired
    private BpmsRepository bpmsRepository;

    @Autowired
    private RepositoryEntityLinks entityLinks;

    @AfterEach
    public void clear_db() {
        this.bpmsRepository.deleteAll();
    }

    @Test
    public void save_and_findById_test() {
        /*
         * bpm body 준비
         */
        List<BpmsSaveDto> bpms = new ArrayList() {
            { add(BpmsSaveDto.builder().value(100).build());
              add(BpmsSaveDto.builder().value(150).build());}};

        /*
         * music 생성
         */
        Music music = Music.builder().title("music").build();
        music = musicsRepository.save(music);
        Link music_link = entityLinks.linkToItemResource(
                MusicsRepository.class, music.getId());

        /*
         * playable 생성
         */
        PlayableSavePayload dto = PlayableSavePayload.builder()
                .level(2)
                .bpms(bpms)
                .music(music_link.toUri()).build();
        PlayablesResponseDto result = playablesService.save(dto);

        assertThat(result.getLevel()).isEqualTo(2);
        assertThat(result.getBpms().size()).isEqualTo(2);
        assertThat(result.getMusic().getTitle()).isEqualTo("music");

        /*
        * Playable get 테스트
         */
        result = playablesService.findById(result.getId());
        assertThat(result.getLevel()).isEqualTo(2);
        assertThat(result.getBpms().size()).isEqualTo(2);
        assertThat(result.getMusic().getTitle()).isEqualTo("music");
    }
}
