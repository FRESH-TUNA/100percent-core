package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.repositories.ComposerRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


// test O
@DataJpaTest
@Import({RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MusicRepositoryTest {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private ComposerRepository composerRepository;

    @Autowired
    private TestEntityManager em;

    private final String 기본_작곡가_이름 = "composer_a", 기본_앨범_이름 = "ALBUM_A", 기본_이름 = "title";
    private final int 기본_min_BPM = 100, 기본_max_BPM = 200;

    @Test
    void 뮤직_저장_테스트() {
        Music music = 뮤직_생성();

        // 저장이 잘되었는지 test
        em.clear();
        music = musicRepository.findById(music.getId()).get();

        //  em.clear안하면 이미 캐시에 있어서 쿼리 안날라간다.
        //  music.getPatterns().size();
        assertThat(music.getTitle()).isEqualTo(기본_이름);
        assertThat(music.getComposers().get(0).getName()).isEqualTo(기본_작곡가_이름);
        assertThat(music.getAlbum().getTitle()).isEqualTo(기본_앨범_이름);
        assertThat(music.getMin_bpm()).isEqualTo(기본_min_BPM);
        assertThat(music.getMax_bpm()).isEqualTo(기본_max_BPM);
    }

    // O
    @Test
    void 뮤직_업데이트_테스트() {
        Music music = 뮤직_생성();
        System.out.println("---------------------");

        // Album
        Album ALBUM_A = Album.builder().title("newalbum").build();
        albumRepository.saveAndFlush(ALBUM_A);
        //composer
        Composer composer = Composer.builder().name("newcomposer").build();
        composerRepository.saveAndFlush(composer);

        // Music
        Music new_music = Music.builder().id(music.getId())
                .album(albumRepository.getById(ALBUM_A.getId()))
                .min_bpm(250).max_bpm(250)
                .title("title2").build();

        // 추가 쿼리가 안날라가는지 test
        em.clear();

        // 연관관계 설정
        new_music.add_composer(composerRepository.getById(composer.getId()));
        composer.add_music(new_music);

        //when
        musicRepository.saveAndFlush(new_music);
        em.clear();
        music = musicRepository.findById(music.getId()).get();

        //then
        assertThat(music.getTitle()).isEqualTo("title2");
        assertThat(music.getComposers().get(0).getName()).isEqualTo("newcomposer");
        assertThat(music.getComposers().size()).isEqualTo(1);
        assertThat(music.getAlbum().getTitle()).isEqualTo("newalbum");
        assertThat(music.getMin_bpm()).isEqualTo(250);
        assertThat(music.getMax_bpm()).isEqualTo(250);
    }

    private Music 뮤직_생성() {
        // Album
        Album ALBUM_A = Album.builder().title(기본_앨범_이름).build();
        albumRepository.saveAndFlush(ALBUM_A);
        //composer
        Composer composer = Composer.builder().name(기본_작곡가_이름).build();
        composerRepository.saveAndFlush(composer);

        // 추가 쿼리가 안날라가는지 test
        em.clear();

        // 연관관계 설정
        List<Composer> composers = new ArrayList<>();
        composers.add(composerRepository.getById(composer.getId()));

        // Music
        Music music = Music.builder()
                .album(albumRepository.getById(ALBUM_A.getId()))
                .min_bpm(기본_min_BPM).max_bpm(기본_max_BPM).composers(composers)
                .title("title").build();

        //when
        return musicRepository.saveAndFlush(music);
    }
}
