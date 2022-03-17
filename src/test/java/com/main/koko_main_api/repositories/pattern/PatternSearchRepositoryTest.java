package com.main.koko_main_api.repositories.pattern;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.DifficultyType;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.PlayType;
import com.main.koko_main_api.repositories.DifficultyTypeRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({PatternSearchRepositoryImpl.class, RepositoryConfig.class})
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

    @Test
    void 뮤직_플레이타입_필터링_테스트() {
        /*
         * given
         */
        // music
        Music music = Music.builder().title("music").build();
        musicRepository.save(music);

        PlayType playType = PlayType.builder().title("5K").build();
        playTypesRepository.save(playType);

        // pattern
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().level(2).music(music).build());
        patterns.add(Pattern.builder().level(2).music(music).playType(playType).build());
        patterns.add(Pattern.builder().level(2).build());
        patternRepository.saveAll(patterns);

        /*
         * when
         */
        patterns = patternRepository.findAllByPlayTypeAndMusics(
                new ArrayList<Long>() {{ add(music.getId()); }}, playType.getId());

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
        Music music = Music.builder().title("music").build();
        musicRepository.save(music);

        PlayType playType = PlayType.builder().title("5K").build();
        playTypesRepository.save(playType);

        // pattern
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().level(3).music(music).build());
        patterns.add(Pattern.builder().level(3).music(music).playType(playType).build());
        patterns.add(Pattern.builder().level(3).music(music).playType(playType).build());
        patterns.add(Pattern.builder().level(2).build());
        patternRepository.saveAll(patterns);

        /*
         * when
         */
        patterns = patternRepository.findAllByPlayTypeAndLevel(playType.getId(), 3);

        /*
         * then
         * music과 playtype이 모두 설정된 1개만 반환된다.
         */
        assertThat(patterns.size()).isEqualTo(2);
    }

    @Test
    void 플레이타입_난이도_필터링_테스트() {
        /*
         * given
         */
        // music
        Music music = Music.builder().title("music").build();
        musicRepository.save(music);

        PlayType playType = PlayType.builder().title("5K").build();
        playTypesRepository.save(playType);

        DifficultyType difficultyType = DifficultyType.builder().name("normal").build();
        difficultyTypeRepository.save(difficultyType);

        // pattern
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().level(3).music(music).build());
        patterns.add(Pattern.builder().level(3).music(music).playType(playType)
                .difficultyType(difficultyType).build());
        patterns.add(Pattern.builder().level(3).music(music).playType(playType)
                .difficultyType(difficultyType).build());
        patterns.add(Pattern.builder().level(2).build());
        patternRepository.saveAll(patterns);

        /*
         * when
         */
        patterns = patternRepository.findAllByPlayTypeAndDifficulty(playType.getId(), difficultyType.getId());

        /*
         * then
         * music과 playtype이 모두 설정된 1개만 반환된다.
         */
        assertThat(patterns.size()).isEqualTo(2);
    }
}
