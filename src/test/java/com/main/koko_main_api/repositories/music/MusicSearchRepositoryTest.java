package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.*;

import com.main.koko_main_api.repositories.album.AlbumRepository;
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

// test O
@DataJpaTest
@Import({MusicSearchRepositoryImpl.class, RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MusicSearchRepositoryTest {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Test
    void 앨범필터링_페이징_테스트() {
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
        }
        for(int i = 0; i < ALBUM_B_N_MUSICS; ++i) {
            Music music = Music.builder().title("music").album(ALBUM_B).build();
            musics.add(music);
        }
        musicRepository.saveAll(musics);

        /*
         * when
         */
        Pageable pageable = PageRequest.of(0, 1);
        Page<Music> FIVE_KEY_musics = musicRepository.findAllByAlbum(pageable, ALBUM_A.getId());
        Page<Music> SIX_KEY_musics = musicRepository.findAllByAlbum(pageable, ALBUM_B.getId());

        assertThat(FIVE_KEY_musics.getNumberOfElements()).isEqualTo(1);
        assertThat(SIX_KEY_musics.getNumberOfElements()).isEqualTo(1);

        assertThat(FIVE_KEY_musics.getTotalElements()).isEqualTo(ALBUM_A_N_MUSICS);
        assertThat(SIX_KEY_musics.getTotalElements()).isEqualTo(ALBUM_B_N_MUSICS);
    }

    @Test
    void 앨범필터링시_앨범이존재하지않을때이테스트() {
        // Album
        Album ALBUM_A = Album.builder().title("ALBUM_A").build();
        Album ALBUM_B = Album.builder().title("ALBUM_B").build();
        albumRepository.save(ALBUM_A);
        albumRepository.save(ALBUM_B);

        // Music save
        Music music = Music.builder().title("music").album(ALBUM_A).build();
        musicRepository.save(music);

        /*
         * when
         */
        Pageable pageable = PageRequest.of(0, 1);
        Page<Music> ALBUM_B_musics = musicRepository.findAllByAlbum(pageable, ALBUM_B.getId());

        assertThat(ALBUM_B_musics.getNumberOfElements()).isEqualTo(0);
        assertThat(ALBUM_B_musics.getTotalElements()).isEqualTo(0);
    }

    @Test
    void 뮤직들의이름으로_필터링_테스트() {
        /*
         * given
         */
        // Album
        Album album = Album.builder().title("album").build();
        albumRepository.save(album);
        // Music
        Pageable pageable = PageRequest.of(0, 1);
        List<Music> musics = new ArrayList<>();
        musics.add(Music.builder().title("music").album(album).build());
        musics.add(Music.builder().title("music2").album(album).build());
        musics.add(Music.builder().title("abc").album(album).build());
        musicRepository.saveAll(musics);

        /*
         * when
         */
        Page<Music> All_musics = musicRepository.findAllByTitle(pageable, "usi");
        assertThat(All_musics.getTotalElements()).isEqualTo(2);
        assertThat(All_musics.getNumberOfElements()).isEqualTo(1);
    }

    @Test
    void 뮤직들의정보로_필터링_테스트() {
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

        /*
         * when
         */
        List<Music> All_musics = musicRepository.findAll(musics);
        assertThat(All_musics.size()).isEqualTo(MUSIC_COUNTS);
    }

    @Test
    void id로찾기테스트() {
        // Album
        Album album = Album.builder().title("album").build();
        albumRepository.save(album);

        Music music = Music.builder().title("music").album(album).build();
        musicRepository.saveAndFlush(music);

        music = musicRepository.findById(music.getId()).get();
    }
}
