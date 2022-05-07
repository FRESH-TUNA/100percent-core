package com.main.koko_main_api.services.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MusicFindAllHelperTest {
    private final MusicFindAllHelper helper = new MusicFindAllHelper();

    @Test
    public void create_test() {
        List<Music> musics = new ArrayList<>();
        musics.add(new Music()); musics.add(new Music());
        Page<Music> music_page = helper.musicsToSinglePage(musics);


        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().music(musics.get(0)).build());
        patterns.add(Pattern.builder().music(musics.get(1)).build());


        /*
         * then
         */
        music_page = helper.create(music_page, patterns);
        musics = music_page.getContent();
        assertThat(musics.size()).isEqualTo(musics.size());
        assertThat(musics.get(0).getPatterns().size()).isEqualTo(1);
        assertThat(musics.get(1).getPatterns().size()).isEqualTo(1);
    }

    @Test
    public void musicsToSinglePage_test() {
        List<Music> musics = new ArrayList<>();
        musics.add(new Music()); musics.add(new Music());

        /*
         * then
         */
        Page<Music> page = helper.musicsToSinglePage(musics);
        assertThat(page.getTotalElements()).isEqualTo(musics.size());
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getSize()).isEqualTo(musics.size());
        assertThat(page.getNumber()).isEqualTo(0);
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