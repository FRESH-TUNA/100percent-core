package com.main.koko_main_api.services.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.dtos.music.MusicListDto;

import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.services.PlayableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/*
 * 참고자료
 * https://tecoble.techcourse.co.kr/post/2021-08-15-pageable/
 */
@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)

public class MusicFindAllServiceTest {
    @InjectMocks
    private MusicFindAllService musicFindAllService;

    @Mock
    private MusicRepository musicRepository;

    @Test
    public void findall_difficulty_filter_test() {
        /*
         * data mocking
         */
        final int MUSIC_LENGTH = 2;
        List<Music> musics = new ArrayList<>();
        Album album = Album.builder().title("album").build();
        DifficultyType hard_type = DifficultyType.builder().name("hard").id(1L).build();
        DifficultyType normal_type = DifficultyType.builder().name("normal").id(2L).build();
        PlayType five_key = PlayType.builder().title("5K").id(1L).build();
        Long playable_id = 1L;

        for(int i = 0; i < MUSIC_LENGTH; ++i) {
            Music music = Music.builder().album(album).build();
            Playable playable_hard_type = Playable.builder()
                    .id(playable_id++).difficultyType(hard_type)
                    .music(music).playType(five_key).build();
            Playable playable_normal_type = Playable.builder()
                    .id(playable_id++).difficultyType(normal_type)
                    .music(music).playType(five_key).build();
            music.add_playable(playable_hard_type);
            music.add_playable(playable_normal_type);
            musics.add(music);
        }

        when(musicRepository.findAll()).thenReturn(musics);

        /*
         * when
         */
        MusicRequestParams hard_params = MusicRequestParams.builder()
                .play_type(five_key.getId())
                .difficulty_type(hard_type.getId())
                .filter("difficulty").build();
        MusicRequestParams normal_params = MusicRequestParams.builder()
                .play_type(five_key.getId())
                .difficulty_type(normal_type.getId())
                .filter("difficulty").build();
        Page<MusicListDto> nomral_result = musicFindAllService.call(null, normal_params);
        Page<MusicListDto> hard_result = musicFindAllService.call(null, hard_params);

        /*
         * then
         */
        assertThat(nomral_result.getContent().get(0).getPlayables().size()).isEqualTo(1);
        assertThat(nomral_result.getContent().get(0).getPlayables().get(0).getDifficultyType().getName()).isEqualTo("normal");
        assertThat(hard_result.getContent().get(0).getPlayables().size()).isEqualTo(1);
        assertThat(hard_result.getContent().get(0).getPlayables().get(0).getDifficultyType().getName()).isEqualTo("hard");
    }

    @Test
    public void findall_level_filter_test() {
        /*
         * data mocking
         */
        final int MUSIC_LENGTH = 2;
        List<Music> musics = new ArrayList<>();
        Album album = Album.builder().title("album").build();
        Integer hard_level = 15, easy_level = 1;
        PlayType five_key = PlayType.builder().title("5K").id(1L).build();
        Long playable_id = 1L;

        for(int i = 0; i < MUSIC_LENGTH; ++i) {
            Music music = Music.builder().album(album).build();
            Playable hard = Playable.builder()
                    .id(playable_id++).level(hard_level)
                    .music(music).playType(five_key).build();
            Playable easy = Playable.builder()
                    .id(playable_id++).level(easy_level)
                    .music(music).playType(five_key).build();
            music.add_playable(hard);
            music.add_playable(easy);
            musics.add(music);
        }

        when(musicRepository.findAll()).thenReturn(musics);

        /*
         * when
         */
        MusicRequestParams hard_params = MusicRequestParams.builder()
                .play_type(five_key.getId())
                .level(hard_level)
                .filter("level").build();
        MusicRequestParams normal_params = MusicRequestParams.builder()
                .play_type(five_key.getId())
                .level(easy_level)
                .filter("level").build();
        Page<MusicListDto> nomral_result = musicFindAllService.call(null, normal_params);
        Page<MusicListDto> hard_result = musicFindAllService.call(null, hard_params);

        /*
         * then
         */
        assertThat(nomral_result.getContent().get(0).getPlayables().size()).isEqualTo(1);
        assertThat(nomral_result.getContent().get(0).getPlayables().get(0).getLevel()).isEqualTo(easy_level);
        assertThat(hard_result.getContent().get(0).getPlayables().size()).isEqualTo(1);
        assertThat(hard_result.getContent().get(0).getPlayables().get(0).getLevel()).isEqualTo(hard_level);
    }
}