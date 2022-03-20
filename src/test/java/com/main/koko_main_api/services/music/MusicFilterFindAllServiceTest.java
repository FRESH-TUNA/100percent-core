package com.main.koko_main_api.services.music;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)

public class MusicFilterFindAllServiceTest {
    @InjectMocks
    private MusicFilterFindAllService musicFilterFindAllService;

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
        List<Long> music_ids = new ArrayList<>();

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

            music.add_playable(pattern_hard_type);

            musics.add(music); patterns.add(pattern_hard_type); music_ids.add(i);
        }

        when(patternRepository.findAllByPlayTypeAndDifficulty(
                five_key.getId(), hard_type.getId())).thenReturn(patterns);

        /*
         * when
         */
        when(patternRepository.findAllByPlayTypeAndDifficulty(
                five_key.getId(), hard_type.getId())).thenReturn(patterns);
        when(musicRepository.findAllByIds(music_ids)).thenReturn(musics);

        /*
         * then
         */
        Page<MusicEntityToServiceDto> page = musicFilterFindAllService
                .findAllByDifficulty(five_key.getId(), hard_type.getId());
        assertThat(page.getSize()).isEqualTo(MUSIC_LENGTH);
    }
}
