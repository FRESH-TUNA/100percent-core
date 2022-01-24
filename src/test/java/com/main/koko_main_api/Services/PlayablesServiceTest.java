package com.main.koko_main_api.Services;

import com.main.koko_main_api.Dtos.BpmsSaveDto;
import com.main.koko_main_api.Dtos.PlayablesResponseDto;
import com.main.koko_main_api.Dtos.PlayablesSaveDto;
import com.main.koko_main_api.Dtos.PlayablesSaveRequestDto;
import com.main.koko_main_api.Models.Bpm;
import com.main.koko_main_api.Models.Music;
import com.main.koko_main_api.Repositories.BpmsRepository;
import com.main.koko_main_api.Repositories.MusicsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
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
    public void save_test() {
        /*
         * bpm body 준비
         */
        List<Bpm> bpms = new ArrayList() {
            { add(BpmsSaveDto.builder().value(100).build().toEntity()); }};

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
        PlayablesSaveRequestDto dto = PlayablesSaveRequestDto.builder()
                .level(2)
                .bpms(bpms)
                .music(music_link.toUri())
                .build();
        PlayablesResponseDto responseDto = playablesService.save(dto);

        // playable 검증
        assertThat(responseDto.getLevel()).isEqualTo(2);
        Iterator<Bpm> it = responseDto.getBpms().iterator();
        assertThat(it.next().getValue()).isEqualTo(100);
        assertThat(responseDto.getMusic().getTitle()).isEqualTo("music");


        /*
         * bpm 생성 검증
         */
        List<Bpm> new_bpms = bpmsRepository.findAll();
        assertThat(new_bpms.get(0).getPlayable().getId()).isEqualTo(responseDto.getId());
    }

    @Test
    public void save_and_findById_test() {
        /*
         * bpm body 준비
         */
        List<Bpm> bpms = new ArrayList() {
            { add(BpmsSaveDto.builder().value(100).build().toEntity()); }};

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
        PlayablesSaveRequestDto dto = PlayablesSaveRequestDto.builder()
                .level(2)
                .bpms(bpms)
                .music(music_link.toUri())
                .build();
        PlayablesResponseDto responseDto = playablesService.save(dto);


        //Playable get해서 검증하기
        PlayablesResponseDto result = playablesService.findById(responseDto.getId());
        assertThat(result.getLevel()).isEqualTo(2);
        Iterator<Bpm> it = result.getBpms().iterator();
        assertThat(it.next().getValue()).isEqualTo(100);
        assertThat(result.getMusic().getTitle()).isEqualTo("music");
    }
}
