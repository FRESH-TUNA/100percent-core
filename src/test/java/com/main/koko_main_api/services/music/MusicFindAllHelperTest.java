package com.main.koko_main_api.services.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MusicFindAllHelperTest {
    private final MusicFindAllHelper helper = new MusicFindAllHelper();

    @Test
    public void create_test() {

    }

    @Test
    public void musicsToSinglePage() {

    }

    @Test
    public void getMusicsFromPatterns_test() {
        Music music1 = Music.builder().build(), music2 = Music.builder().build();
        List<Pattern> patterns = new ArrayList<>();

        patterns.add(Pattern.builder().music(music1).build());
        patterns.add(Pattern.builder().music(music2).build());
        patterns.add(Pattern.builder().music(music1).build());
        /*
         * then
         */
        List<Music> musics = helper.getMusicsFromPatterns(patterns);
        assertThat(musics.size()).isEqualTo(2);
        assertThat(musics.get(0)).isEqualTo(music1);
        assertThat(musics.get(1)).isEqualTo(music2);
    }
}