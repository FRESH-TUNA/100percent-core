package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.repositories.DifficultyTypeRepository;
import com.main.koko_main_api.repositories.PlayTypesRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;

import com.querydsl.core.Tuple;
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
//    @Test
//    void test_findAll() {
//        /*
//         * given
//         */
//        final int MUSIC_COUNTS = 5;
//
//        // Album
//        Album album = Album.builder().title("album").build();
//        albumRepository.save(album);
//
//        // Music
//        List<Music> musics = new ArrayList<>();
//        for(int i = 0; i < MUSIC_COUNTS; ++i)
//            musics.add(Music.builder().title("music").album(album).build());
//        musicRepository.saveAll(musics);
//
//        //Playtype
//        PlayType playType = PlayType.builder().title("5K").build();
//        playTypesRepository.save(playType);
//
//        // Playables
//        List<Pattern> patterns = new ArrayList<>();
//        for(int i = 0; i < MUSIC_COUNTS; ++i) {
//            Music music = musics.get(i);
//            Pattern pattern1 = Pattern.builder().level(2).music(music).playType(playType).build();
//            Pattern pattern2 = Pattern.builder().level(10).music(music).playType(playType).build();
//            patterns.add(Pattern.builder().level(2).music(music).build());
//            music.add_playable(pattern1);
//            music.add_playable(pattern2);
//        }
//        patternRepository.saveAll(patterns);
//
//        /*
//         * when
//         */
//        List<Music> All_musics = musicRepository.findAll();
//        assertThat(All_musics.size()).isEqualTo(MUSIC_COUNTS);
//        assertThat(All_musics.get(0).getAlbum().getTitle()).isEqualTo("album");
//        assertThat(All_musics.get(0).getPatterns().size()).isEqualTo(2);
//    }
}
