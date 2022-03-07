package com.main.koko_main_api.services.music;

import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.music.MusicEntityToServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class MusicAddPatternsResultServiceTest {
    @Test
    public void 테스트() {
        /*
         * data mocking
         */
        final int MUSIC_LENGTH = 2;
        List<Music> musics = new ArrayList<>();
        Long playable_id = 1L;
        List<Pattern> patterns = new ArrayList<>();
        Album album = Album.builder().title("album").build();
        Pageable page = PageRequest.of(0, 2);

        for(int i = 0; i < MUSIC_LENGTH; ++i) {
            Music music = Music.builder().id((long) i).album(album).build();
            musics.add(music);

            Pattern pattern_hard_type = Pattern.builder()
                    .id(playable_id++)
                    .music(music).build();

            Pattern pattern_normal_type = Pattern.builder()
                    .id(playable_id++)
                    .music(music).build();

            patterns.add(pattern_hard_type);
            patterns.add(pattern_normal_type);
        }

        /*
         * when
         */
        MusicAddPatternsResultService service = new MusicAddPatternsResultService();
        Page<MusicEntityToServiceDto> result =  service.call(
                new PageImpl(musics, page, musics.size()), patterns);

        /*
         * then
         */
        assertThat(result.getContent().get(0).getPlayables().size()).isEqualTo(2);
        assertThat(result.getContent().get(1).getPlayables().size()).isEqualTo(2);
    }
}