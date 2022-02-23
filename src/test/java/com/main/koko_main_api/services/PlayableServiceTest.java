package com.main.koko_main_api.services;

import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.dtos.music.MusicBpmSaveEntityDto;
import com.main.koko_main_api.dtos.playable.PlayableDetailResponseEntityDto;
import com.main.koko_main_api.payloads.playable.PlayableSavePayload;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.repositories.BpmRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.playable.PlayableRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 * 참고자료
 * https://tecoble.techcourse.co.kr/post/2021-08-15-pageable/
 */
@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
public class PlayableServiceTest {

    @InjectMocks
    private PlayableService playableService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private BpmRepository bpmRepository;

    @Mock
    private PlayableRepository playableRepository;

    @Test
    public void save_test() throws URISyntaxException {
        /*
         * data mocking
         */
        List<MusicBpmSaveEntityDto> bpms = new ArrayList() {
            { add(MusicBpmSaveEntityDto.builder().value(100).build());
              add(MusicBpmSaveEntityDto.builder().value(150).build());}};
        Music saved_music = Music.builder().title("music").id(1L).build();
        URI music_link = new URI("http://localhost/musics/1");
        PlayableSavePayload playableSavePayload = PlayableSavePayload.builder()
                .level(2)
                .bpms(bpms)
                .music(music_link).build();
        Playable playable = Playable.builder()
                .level(2)
                .id(1L)
                .music(saved_music)
                .build();
//        List<Bpm> _bpms = bpms.stream().map(
//                bpm -> bpm.toEntity(playable)).collect(Collectors.toList());

        when(musicRepository.findById(1L)).thenReturn(Optional.of(saved_music));
        when(playableRepository.save(any())).thenReturn(playable);
//        when(bpmRepository.saveAll(any())).thenReturn(_bpms);

        /*
         * when
         */
        PlayableDetailResponseEntityDto result = playableService.save(playableSavePayload);

        /*
         * then
         */
        assertThat(result.getLevel()).isEqualTo(2);
//        assertThat(result.getBpms().size()).isEqualTo(2);
//        assertThat(result.getBpms().get(0).getValue()).isEqualTo(100);
//        assertThat(result.getBpms().get(1).getValue()).isEqualTo(150);
        assertThat(result.getMusic().getTitle()).isEqualTo("music");
    }

    @Test
    public void findById_test() {
        /*
         * data mocking
         */
        List<MusicBpmSaveEntityDto> bpms = new ArrayList() {
            { add(MusicBpmSaveEntityDto.builder().value(100).build());
                add(MusicBpmSaveEntityDto.builder().value(150).build());}};
        Music saved_music = Music.builder().title("music").id(1L).build();
        Playable playable = Playable.builder()
                .level(2)
                .id(1L)
                .music(saved_music)
                .build();
//        playable.add_bpms_for_save_request(bpms.stream()
//                .map(bpm -> bpm.toEntity(playable))
//                .collect(Collectors.toList()));


        when(playableRepository.findById(1L)).thenReturn(Optional.of(playable));

        /*
         * when
         */
        PlayableDetailResponseEntityDto result = playableService.findById(1L);

        /*
         * then
         */
        assertThat(result.getLevel()).isEqualTo(2);
//        assertThat(result.getBpms().size()).isEqualTo(2);
//        assertThat(result.getBpms().get(0).getValue()).isEqualTo(100);
//        assertThat(result.getBpms().get(1).getValue()).isEqualTo(150);
        assertThat(result.getMusic().getTitle()).isEqualTo("music");
    }

    @Test
    public void findAll_test() {
        /*
         * data and method mocking
         */
        List<MusicBpmSaveEntityDto> playable1_bpms = new ArrayList() {
            { add(MusicBpmSaveEntityDto.builder().value(100).build());
                add(MusicBpmSaveEntityDto.builder().value(150).build());}};
        List<MusicBpmSaveEntityDto> playable2_bpms = new ArrayList() {
            { add(MusicBpmSaveEntityDto.builder().value(100).build());
                add(MusicBpmSaveEntityDto.builder().value(150).build());}};

        Music saved_music_1 = Music.builder().title("music1").id(1L).build();
        Music saved_music_2 = Music.builder().title("music2").id(2L).build();

        Playable playable1 = Playable.builder()
                .level(2)
                .id(1L)
                .music(saved_music_1)
                .build();
        Playable playable2 = Playable.builder()
                .level(3)
                .id(2L)
                .music(saved_music_2)
                .build();

//        playable1.add_bpms_for_save_request(playable1_bpms.stream()
//                .map(bpm -> bpm.toEntity(playable1))
//                .collect(Collectors.toList()));
//        playable2.add_bpms_for_save_request(playable2_bpms.stream()
//                .map(bpm -> bpm.toEntity(playable2))
//                .collect(Collectors.toList()));

        Page<Playable> page = new PageImpl<>(
                new ArrayList<Playable>() {{ add(playable1); add(playable2); }});
        Pageable pageable = PageRequest.of(0, 10);

        when(playableRepository.findAll(pageable)).thenReturn(page);

        /*
         * when
         */
//        Page<PlayableListResponseEntityDto> results = playableService.findAll(pageable);
//
//        List<PlayableListResponseEntityDto> results_list = results.getContent();
//        PlayableListResponseEntityDto playable1_res = results_list.get(0),
//                playable2_res = results_list.get(1);
//
//        /*
//         * then
//         */
//        assertThat(results.getNumber()).isEqualTo(0);
//        assertThat(results.getSize()).isEqualTo(2);
//        assertThat(results.getTotalElements()).isEqualTo(2);
//        assertThat(results.getTotalPages()).isEqualTo(1);
//
//        assertThat(playable1_res.getBpms().size()).isEqualTo(2);
//        assertThat(playable2_res.getBpms().size()).isEqualTo(2);
    }
}
