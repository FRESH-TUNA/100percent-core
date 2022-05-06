package com.main.koko_main_api.services;

import com.main.koko_main_api.domains.Pattern;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;

import com.main.koko_main_api.services.music.MusicPatternService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 * 참고자료
 * https://tecoble.techcourse.co.kr/post/2021-08-15-pageable/
 */
@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
public class PatternServiceTest {

    @InjectMocks
    private MusicPatternService musicPatternService;

    @Mock
    private MusicRepository musicRepository;


    @Mock
    private PatternRepository patternRepository;

//    @Test
//    public void save_test() throws URISyntaxException {
//        /*
//         * data mocking
//         */
//
//        Music saved_music = Music.builder().title("music").id(1L).build();
//        URI music_link = new URI("http://localhost/musics/1");
//        PatternRequestDto patternRequestDto = PatternRequestDto.builder()
//                .level(2)
//                .music(music_link).build();
//        Pattern pattern = Pattern.builder()
//                .level(2)
//                .id(1L)
//                .music(saved_music)
//                .build();
////        List<Bpm> _bpms = bpms.stream().map(
////                bpm -> bpm.toEntity(playable)).collect(Collectors.toList());
//
//        when(musicRepository.findById(1L)).thenReturn(Optional.of(saved_music));
//        when(patternRepository.save(any())).thenReturn(pattern);
////        when(bpmRepository.saveAll(any())).thenReturn(_bpms);
//
//        /*
//         * when
//         */
//        PatternDetailResponseEntityDto result = patternService.save(patternRequestDto);
//
//        /*
//         * then
//         */
//        assertThat(result.getLevel()).isEqualTo(2);
////        assertThat(result.getBpms().size()).isEqualTo(2);
////        assertThat(result.getBpms().get(0).getValue()).isEqualTo(100);
////        assertThat(result.getBpms().get(1).getValue()).isEqualTo(150);
//        assertThat(result.getMusic().getTitle()).isEqualTo("music");
//    }

//    @Test
//    public void findById_test() {
//        /*
//         * data mocking
//         */
//
//        Music saved_music = Music.builder().title("music").id(1L).build();
//        Pattern pattern = Pattern.builder()
//                .level(2)
//                .id(1L)
//                .music(saved_music)
//                .build();
////        playable.add_bpms_for_save_request(bpms.stream()
////                .map(bpm -> bpm.toEntity(playable))
////                .collect(Collectors.toList()));
//
//
//        when(patternRepository.findById(1L)).thenReturn(Optional.of(pattern));
//
//        /*
//         * when
//         */
//        PatternDetailResponseEntityDto result = patternService.findById(1L);
//
//        /*
//         * then
//         */
//        assertThat(result.getLevel()).isEqualTo(2);
////        assertThat(result.getBpms().size()).isEqualTo(2);
////        assertThat(result.getBpms().get(0).getValue()).isEqualTo(100);
////        assertThat(result.getBpms().get(1).getValue()).isEqualTo(150);
//        assertThat(result.getMusic().getTitle()).isEqualTo("music");
//    }

    @Test
    public void findAll_test() {
        /*
         * data and method mocking
         */

        Music saved_music_1 = Music.builder().title("music1").id(1L).build();
        Music saved_music_2 = Music.builder().title("music2").id(2L).build();

        Pattern pattern1 = Pattern.builder()
                .level(2)
                .id(1L)
                .music(saved_music_1)
                .build();
        Pattern pattern2 = Pattern.builder()
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

        Page<Pattern> page = new PageImpl<>(
                new ArrayList<Pattern>() {{ add(pattern1); add(pattern2); }});
        Pageable pageable = PageRequest.of(0, 10);

        when(patternRepository.findAll(pageable)).thenReturn(page);

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
