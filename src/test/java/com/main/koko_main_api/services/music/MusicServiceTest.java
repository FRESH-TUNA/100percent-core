package com.main.koko_main_api.services.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.*;

import com.main.koko_main_api.dtos.music.MusicEntityToServiceDto;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/*
 * 참고자료
 * https://tecoble.techcourse.co.kr/post/2021-08-15-pageable/
 * test complete
 */
@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)

public class MusicServiceTest {
    @InjectMocks
    private MusicService musicService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private PatternRepository patternRepository;

    @Test
    public void 앨범없이_플레이타입으로_필터후_페이징() {
        /*
         * data mocking
         */
        final int MUSIC_LENGTH = 2;
        List<Music> musics = new ArrayList<>();
        List<Long> music_ids = new ArrayList<>();
        Album album = Album.builder().title("album").build();
        PlayType five_key = PlayType.builder().title("5K").id(1L).build();
        PlayType six_key = PlayType.builder().title("6K").id(2L).build();
        List<Pattern> five_patterns = new ArrayList<>();
        List<Pattern> six_patterns = new ArrayList<>();
        Long playable_id = 1L;

        for(int i = 0; i < MUSIC_LENGTH; ++i) {
            Music music = Music.builder().album(album).id((long) i).build();
            musics.add(music);
            music_ids.add((long) i);

            Pattern pattern_hard_type = Pattern.builder()
                    .id(playable_id++)
                    .music(music).playType(five_key).build();

            Pattern pattern_normal_type = Pattern.builder()
                    .id(playable_id++)
                    .music(music).playType(six_key).build();

            music.add_playable(pattern_hard_type);
            five_patterns.add(pattern_hard_type);

            music.add_playable(pattern_normal_type);
            six_patterns.add(pattern_hard_type);
        }

        Pageable page = PageRequest.of(0, 1);
        Page<Music> music_page = new PageImpl<>(musics, page, MUSIC_LENGTH);

        when(musicRepository.findAll(page)).thenReturn(music_page);
        when(patternRepository.findAllByPlayTypeAndMusics(music_ids, five_key.getId()))
                .thenReturn(five_patterns);
        when(patternRepository.findAllByPlayTypeAndMusics(music_ids, six_key.getId()))
                .thenReturn(six_patterns);

        /*
         * when
         */
//        MusicRequestParams hard_params = MusicRequestParams.builder()
//                .play_type(five_key.getId()).mode("db").build();
//        MusicRequestParams normal_params = MusicRequestParams.builder()
//                .play_type(six_key.getId()).build();
        Page<MusicEntityToServiceDto> five_key_result = musicService.findAll(page, five_key.getId());
        Page<MusicEntityToServiceDto> six_key_result = musicService.findAll(page, six_key.getId());

        /*
         * then
         */
        assertThat(five_key_result.getTotalElements()).isEqualTo(2);
        assertThat(six_key_result.getTotalElements()).isEqualTo(2);

        assertThat(five_key_result.getContent().get(0).getPlayables().size()).isEqualTo(1);
        assertThat(five_key_result.getContent().get(0).getPlayables().size()).isEqualTo(1);
    }
}
