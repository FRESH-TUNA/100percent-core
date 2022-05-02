package com.main.koko_main_api.repositories.pattern;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.repositories.DifficultyTypeRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;

import com.main.koko_main_api.repositories.PlayTypesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * test O
 */
@DataJpaTest
@Import(RepositoryConfig.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatternSearchRepositoryTest {
    @Autowired
    private PatternRepository patternRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private PlayTypesRepository playTypesRepository;

    @Autowired
    private DifficultyTypeRepository difficultyTypeRepository;

    @Autowired
    private AlbumRepository albumRepository;


    private Album 앨범만들어서반환() {
        Album album = Album.builder().title("album").build();
        albumRepository.save(album);
        return album;
    }

    @Test
    void 뮤직_플레이타입_필터링_테스트() {
        /*
         * given
         */
        // music
        Music music1 = Music.builder().title("music1").album(앨범만들어서반환()).build();
        Music music2 = Music.builder().title("music2").album(앨범만들어서반환()).build();
        musicRepository.save(music1);
        musicRepository.save(music2);

        PlayType playType1 = PlayType.builder().title("5K").build();
        PlayType playType2 = PlayType.builder().title("4K").build();
        playTypesRepository.save(playType1);
        playTypesRepository.save(playType2);

        DifficultyType difficultyType = DifficultyType.builder().name("normal").build();
        difficultyTypeRepository.save(difficultyType);

        // pattern
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().level(2).music(music1)
                .difficultyType(difficultyType).playType(playType2).build());
        patterns.add(Pattern.builder().level(2).music(music1)
                .difficultyType(difficultyType).playType(playType1).build());
        patterns.add(Pattern.builder().level(2).music(music2)
                .difficultyType(difficultyType).playType(playType1).build());
        patternRepository.saveAll(patterns);

        /*
         * when
         */
        patterns = patternRepository.findAllByPlayTypeAndMusics(
                new ArrayList<Music>() {{ add(music1); }}, playType2.getId());

        /*
         * then
         * music과 playtype이 모두 설정된 1개만 반환된다.
         */
        assertThat(patterns.size()).isEqualTo(1);
    }

    @Test
    void 플레이타입_레벨_필터링_테스트() {
        /*
         * given
         */
        // music
        Music music = Music.builder().title("music").album(앨범만들어서반환()).build();
        musicRepository.save(music);

        PlayType playType1 = PlayType.builder().title("5K").build();
        PlayType playType2 = PlayType.builder().title("4K").build();
        playTypesRepository.save(playType1);
        playTypesRepository.save(playType2);

        DifficultyType difficultyType = DifficultyType.builder().name("normal").build();
        difficultyTypeRepository.save(difficultyType);

        // pattern
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().level(2).music(music)
                .difficultyType(difficultyType).playType(playType1).build());
        patterns.add(Pattern.builder().level(3).music(music)
                .difficultyType(difficultyType).playType(playType1).build());
        patterns.add(Pattern.builder().level(3).music(music)
                .difficultyType(difficultyType).playType(playType2).build());
        patternRepository.saveAll(patterns);

        /*
         * when
         */
        patterns = patternRepository.findAllByPlayTypeAndLevel(playType1.getId(), 3);

        /*
         * then
         * music과 playtype이 모두 설정된 1개만 반환된다.
         */
        assertThat(patterns.size()).isEqualTo(1);
    }

    @Test
    void 플레이타입_난이도_필터링_테스트() {
        /*
         * given
         */
        // music
        Music music = Music.builder().title("music").album(앨범만들어서반환()).build();
        musicRepository.save(music);

        PlayType playType1 = PlayType.builder().title("5K").build();
        PlayType playType2 = PlayType.builder().title("4K").build();
        playTypesRepository.save(playType1);
        playTypesRepository.save(playType2);

        DifficultyType difficultyType1 = DifficultyType.builder().name("normal").build();
        DifficultyType difficultyType2 = DifficultyType.builder().name("hard").build();
        difficultyTypeRepository.save(difficultyType1);
        difficultyTypeRepository.save(difficultyType2);

        // pattern
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().level(3).music(music).playType(playType2)
                .difficultyType(difficultyType1).build());
        patterns.add(Pattern.builder().level(3).music(music).playType(playType1)
                .difficultyType(difficultyType1).build());
        patterns.add(Pattern.builder().level(3).music(music).playType(playType1)
                .difficultyType(difficultyType2).build());
        patternRepository.saveAll(patterns);

        /*
         * when
         */
        patterns = patternRepository.findAllByPlayTypeAndDifficulty(
                playType1.getId(), difficultyType1.getId());

        /*
         * then
         * music과 playtype이 모두 설정된 1개만 반환된다.
         */
        assertThat(patterns.size()).isEqualTo(1);
    }

    @Test
    void id들로_필터링_테스트() {
        // music
        Music music = Music.builder().title("music").album(앨범만들어서반환()).build();
        musicRepository.save(music);

        PlayType playType1 = PlayType.builder().title("5K").build();
        PlayType playType2 = PlayType.builder().title("4K").build();
        playTypesRepository.save(playType1);
        playTypesRepository.save(playType2);

        DifficultyType difficultyType1 = DifficultyType.builder().name("normal").build();
        DifficultyType difficultyType2 = DifficultyType.builder().name("hard").build();
        difficultyTypeRepository.save(difficultyType1);
        difficultyTypeRepository.save(difficultyType2);

        // pattern
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().level(3).music(music).playType(playType2)
                .difficultyType(difficultyType1).build());
        patterns.add(Pattern.builder().level(3).music(music).playType(playType1)
                .difficultyType(difficultyType1).build());
        patterns.add(Pattern.builder().level(3).music(music).playType(playType1)
                .difficultyType(difficultyType2).build());
        patternRepository.saveAllAndFlush(patterns);


        patterns = patternRepository.findAllById(patterns.stream().map(x -> x.getId()).collect(Collectors.toList()));
        assertThat(patterns.size()).isEqualTo(2);
    }
}
