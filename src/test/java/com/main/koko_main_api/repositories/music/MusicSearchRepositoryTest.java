package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.repositories.PlayTypesRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.playable.PlayableRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({MusicSearchRepositoryImpl.class, RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MusicSearchRepositoryTest {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private PlayableRepository playableRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private PlayTypesRepository playTypesRepository;

    @Test
    void test_findAll_pageable_album_id() {
        /*
         * given
         */
        final int ALBUM_A_N_MUSICS = 2;
        final int ALBUM_B_N_MUSICS = 3;

        // Album
        Album ALBUM_A = Album.builder().title("ALBUM_A").build();
        Album ALBUM_B = Album.builder().title("ALBUM_B").build();
        albumRepository.save(ALBUM_A);
        albumRepository.save(ALBUM_B);

        // Music
        List<Music> musics = new ArrayList<>();
        for(int i = 0; i < ALBUM_A_N_MUSICS; ++i) {
            Music music = Music.builder().title("music").album(ALBUM_A).build();
            musics.add(music);
            musicRepository.save(music);
        }
        for(int i = 0; i < ALBUM_B_N_MUSICS; ++i) {
            Music music = Music.builder().title("music").album(ALBUM_B).build();
            musics.add(music);
            musicRepository.save(music);
        }

        // Playables
        List<Playable> playables = new ArrayList<>();
        for(int i = 0; i < ALBUM_A_N_MUSICS + ALBUM_B_N_MUSICS; ++i) {
            Music music = musics.get(i);
            Playable playable1 = Playable.builder().level(2).music(music).build();
            Playable playable2 = Playable.builder().level(10).music(music).build();
            playables.add(Playable.builder().level(2).music(music).build());
            music.add_playable(playable1);
            music.add_playable(playable2);
        }
        playableRepository.saveAll(playables);

        /*
         * when
         */
        Pageable pageable = PageRequest.of(0, 5);
        Page<Music> All_musics = musicRepository.findAll(pageable, null);
        Page<Music> FIVE_KEY_musics = musicRepository.findAll(pageable, ALBUM_A.getId());
        Page<Music> SIX_KEY_musics = musicRepository.findAll(pageable, ALBUM_B.getId());

        assertThat(All_musics.getNumberOfElements()).isEqualTo(ALBUM_A_N_MUSICS + ALBUM_B_N_MUSICS);
        assertThat(FIVE_KEY_musics.getNumberOfElements()).isEqualTo(ALBUM_A_N_MUSICS);
        assertThat(SIX_KEY_musics.getNumberOfElements()).isEqualTo(ALBUM_B_N_MUSICS);
        assertThat(FIVE_KEY_musics.getContent().get(0).getPlayables().size()).isEqualTo(2);
    }

    @Test
    void test_findAll() {
        /*
         * given
         */
        final int MUSIC_COUNTS = 5;

        // Album
        Album album = Album.builder().title("album").build();
        albumRepository.save(album);

        // Music
        List<Music> musics = new ArrayList<>();
        for(int i = 0; i < MUSIC_COUNTS; ++i)
            musics.add(Music.builder().title("music").album(album).build());
        musicRepository.saveAll(musics);

        //Playtype
        PlayType playType = PlayType.builder().title("5K").build();
        playTypesRepository.save(playType);

        // Playables
        List<Playable> playables = new ArrayList<>();
        for(int i = 0; i < MUSIC_COUNTS; ++i) {
            Music music = musics.get(i);
            Playable playable1 = Playable.builder().level(2).music(music).playType(playType).build();
            Playable playable2 = Playable.builder().level(10).music(music).playType(playType).build();
            playables.add(Playable.builder().level(2).music(music).build());
            music.add_playable(playable1);
            music.add_playable(playable2);
        }
        playableRepository.saveAll(playables);

        /*
         * when
         */
        List<Music> All_musics = musicRepository.findAll();
        assertThat(All_musics.size()).isEqualTo(MUSIC_COUNTS);
        assertThat(All_musics.get(0).getAlbum().getTitle()).isEqualTo("album");
        assertThat(All_musics.get(0).getPlayables().size()).isEqualTo(2);
    }
}
