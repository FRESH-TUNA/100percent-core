package com.main.koko_main_api.services;

import com.main.koko_main_api.dtos.playable.bpm.BpmsSaveDto;
import com.main.koko_main_api.dtos.playable.PlayableDetailResponse;
import com.main.koko_main_api.dtos.playable.PlayableSavePayload;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.repositories.BpmsRepository;
import com.main.koko_main_api.repositories.MusicsRepository;
import com.main.koko_main_api.repositories.PlayablesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
@Transactional
public class PlayableServiceTest {

    @InjectMocks
    private PlayableService playableService;

    @Mock
    private MusicsRepository musicsRepository;

    @Mock
    private BpmsRepository bpmsRepository;

    @Mock
    private RepositoryEntityLinks entityLinks;

    @Mock
    private PlayablesRepository playablesRepository;

    @Mock
    private UriToIDService uriToIDService;

//    aftereach 를 통해 후처리를 할수 있다.
//    @AfterEach
//    public void clear_db() {
//        this.bpmsRepository.deleteAll();
//    }

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
        PlayableDetailResponse result = playableService.save(dto);

        assertThat(result.getLevel()).isEqualTo(2);
        assertThat(result.getBpms().size()).isEqualTo(2);
        assertThat(result.getMusic().getTitle()).isEqualTo("music");

        /*
        * Playable get 테스트
         */
        result = playableService.findById(result.getId());
        assertThat(result.getLevel()).isEqualTo(2);
        assertThat(result.getBpms().size()).isEqualTo(2);
        assertThat(result.getMusic().getTitle()).isEqualTo("music");
    }
}
