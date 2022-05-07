package com.main.koko_main_api.services.music;
import com.main.koko_main_api.domains.*;

import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import com.main.koko_main_api.services.MusicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)

public class MusicFilterFindAllServiceTest {
    @InjectMocks
    private MusicService musicFilterFindAllService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private PatternRepository patternRepository;

    @Test
    public void 난이도_타입_필터링_테스트() {
        /*
         * data mocking
         */
        //main
        final int MUSIC_LENGTH = 2;
        List<Music> musics = new ArrayList<>();
        List<Pattern> patterns = new ArrayList<>();

        //sub
        Album album = Album.builder().title("album").build();
        DifficultyType hard_type = DifficultyType.builder().name("hard").id(1L).build();
        PlayType five_key = PlayType.builder().title("5K").id(1L).build();
        Long playable_id = 1L;

        //init
        for(long i = 0; i < MUSIC_LENGTH; ++i) {
            Music music = Music.builder().album(album).id(i).build();

            Pattern pattern_hard_type = Pattern.builder()
                    .id(playable_id++).difficultyType(hard_type)
                    .music(music).playType(five_key).build();

            music.add_pattern(pattern_hard_type);

            musics.add(music); patterns.add(pattern_hard_type);
        }

        /*
         * when
         */
        when(patternRepository.findAllByPlayTypeAndDifficulty(
                five_key.getId(), hard_type.getId())).thenReturn(patterns);
        when(musicRepository.findAll(musics)).thenReturn(musics);

        /*
         * then
         */
        PagedModel<MusicResponseDto> page = musicFilterFindAllService
                .findAllByDifficulty(five_key.getId(), hard_type.getId());
        assertThat(page.getContent().size()).isEqualTo(MUSIC_LENGTH);
    }

    @Test
    public void 레벨_플레이타입_필터링_테스트() {
        /*
         * data mocking
         */
        //main
        final int MUSIC_LENGTH = 2;
        List<Music> musics = new ArrayList<>();
        List<Pattern> patterns = new ArrayList<>();
        List<Long> music_ids = new ArrayList<>();

        //sub
        Album album = Album.builder().title("album").build();
        Integer level = 15;
        PlayType five_key = PlayType.builder().title("5K").id(1L).build();
        Long playable_id = 1L;

        //init
        for(long i = 0; i < MUSIC_LENGTH; ++i) {
            Music music = Music.builder().album(album).id(i).build();

            Pattern pattern_hard_type = Pattern.builder()
                    .id(playable_id++).level(level)
                    .music(music).playType(five_key).build();

            music.add_pattern(pattern_hard_type);

            musics.add(music); patterns.add(pattern_hard_type);
        }

        /*
         * when
         */
        when(patternRepository.findAllByPlayTypeAndLevel(
                five_key.getId(), level)).thenReturn(patterns);
        when(musicRepository.findAll(musics)).thenReturn(musics);

        /*
         * then
         */
        PagedModel<MusicResponseDto> page = musicFilterFindAllService
                .findAllByLevel(five_key.getId(), level);
        assertThat(page.getContent().size()).isEqualTo(MUSIC_LENGTH);
    }
}
