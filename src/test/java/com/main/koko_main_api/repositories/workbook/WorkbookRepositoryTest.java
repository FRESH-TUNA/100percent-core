package com.main.koko_main_api.repositories.workbook;

import com.main.koko_main_api.configs.RepositoryConfig;

import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.repositories.DifficultyTypeRepository;
import com.main.koko_main_api.repositories.PlayTypesRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkbookRepositoryTest {
    @Autowired
    private WorkbookRepository workbookRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private PlayTypesRepository playTypesRepository;

    @Autowired
    private DifficultyTypeRepository difficultyTypeRepository;

    @Autowired
    private PatternRepository patternRepository;

    private final String 기본_작곡가_이름 = "composer_a", 기본_앨범_이름 = "ALBUM_A", 기본_이름 = "title";
    private final int 기본_min_BPM = 100, 기본_max_BPM = 200;

    @Test
    void id로찾기_테스트() {
        Workbook workbook = workbookRepository.saveAndFlush(new Workbook());

        Workbook workbook2 = workbookRepository.findById(workbook.getId()).get();

        assertThat(workbook).isEqualTo(workbook2);
    }

    @Test
    void 페이지로_findAll_테스트() {
        PlayType playType = 플레이타입_생성();
        Workbook workbook1 = Workbook.builder().title("title").description("des").playType(playType).build();
        Workbook workbook2 = Workbook.builder().title("title").description("des").playType(playType).build();
        Workbook workbook3 = Workbook.builder().title("title").description("des").playType(playType).build();

        workbookRepository.saveAndFlush(workbook1);
        workbookRepository.saveAndFlush(workbook2);
        workbookRepository.saveAndFlush(workbook3);

        Page<Workbook> workbooks = workbookRepository.findAll(PageRequest.of(0, 2));
        assertThat(workbooks.getTotalElements()).isEqualTo(3);
        assertThat(workbooks.getSize()).isEqualTo(2);
    }

    @Test
    void 페이지로_playtype_findAll_테스트() {
        PlayType playType1 = 플레이타입_생성(), playType2 = 플레이타입_생성();

        Workbook workbook1 = Workbook.builder().title("title").description("des").playType(playType1).build();
        Workbook workbook2 = Workbook.builder().title("title").description("des").playType(playType1).build();
        Workbook workbook3 = Workbook.builder().title("title").description("des").playType(playType2).build();
        Workbook workbook4 = Workbook.builder().title("title").description("des").playType(playType2).build();

        workbookRepository.saveAndFlush(workbook1);
        workbookRepository.saveAndFlush(workbook2);
        workbookRepository.saveAndFlush(workbook3);
        workbookRepository.saveAndFlush(workbook4);

        Page<Workbook> workbooks = workbookRepository.findAll(PageRequest.of(0, 2), playType1.getId());
        assertThat(workbooks.getTotalElements()).isEqualTo(2);
        assertThat(workbooks.getSize()).isEqualTo(2);
    }

    @Test
    void 생성_테스트() {
        PlayType playType = 플레이타입_생성();
        Workbook workbook = Workbook.builder().title("title").description("des").playType(playType).build();

        List<WorkbookPattern> patterns = new ArrayList<>();
        patterns.add(WorkbookPattern.builder().pattern(패턴_생성()).workbook(workbook).build());
        patterns.add(WorkbookPattern.builder().pattern(패턴_생성()).workbook(workbook).build());
        workbook.add_patterns(patterns);

        workbook = workbookRepository.saveAndFlush(workbook);
        assertThat(workbook.getPatterns().size()).isEqualTo(2);
    }

    private Music 뮤직_생성() {
        // Album
        Album ALBUM_A = Album.builder().title(기본_앨범_이름).build();
        albumRepository.saveAndFlush(ALBUM_A);

        // Music
        Music music = Music.builder()
                .album(albumRepository.getById(ALBUM_A.getId()))
                .min_bpm(기본_min_BPM).max_bpm(기본_max_BPM)
                .title("title").build();
        //when
        return musicRepository.saveAndFlush(music);
    }

    private Pattern 패턴_생성() {
        return patternRepository.saveAndFlush(Pattern.builder()
                .music(뮤직_생성())
                .difficultyType(난이도타입_생성())
                .playType(플레이타입_생성())
                .build());
    }

    private DifficultyType 난이도타입_생성() {
        return difficultyTypeRepository.save(DifficultyType.builder().name("name").build());
    }

    private PlayType 플레이타입_생성() {
        return playTypesRepository.save(PlayType.builder().title("title").build());
    }
}