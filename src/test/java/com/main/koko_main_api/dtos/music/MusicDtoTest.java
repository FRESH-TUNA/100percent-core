package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.music.bpm.MusicAlbumDto;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsDto;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class MusicDtoTest {

    @Test
    public void 객체_기본_속성_테스트() {
        Album album = Album.builder().title("album").id(2L).build();
        Music music = Music.builder().title("title").id(1L).album(album).build();

        /*
         * then
         */
        MusicDto dto = new MusicDto(music);

        /*
         * when
         */
        assertThat(dto.getId()).isEqualTo(music.getId());
        assertThat(dto.getTitle()).isEqualTo(music.getTitle());
        assertThat(dto.getAlbum().getClass()).isEqualTo(MusicAlbumDto.class);
        assertThat(dto.getPatterns().getClass()).isEqualTo(ArrayList.class);
    }

    @Test
    public void addMusicPatternDto_test() {
        Album album = Album.builder().title("album").id(2L).build();
        Music music = Music.builder().title("title").id(1L).album(album).build();

        /*
         * then
         */
        MusicDto dto = new MusicDto(music);
        dto.addMusicPatternDto(new MusicPatternsDto(new Pattern()));

        /*
         * when
         */
        assertThat(dto.getPatterns().size()).isEqualTo(1);
    }
}
