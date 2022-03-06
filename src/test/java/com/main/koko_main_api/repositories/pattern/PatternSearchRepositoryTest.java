package com.main.koko_main_api.repositories.pattern;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.repositories.BpmRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;

import com.main.koko_main_api.repositories.PlayTypesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

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
    private BpmRepository bpmRepository;

    @Autowired
    private PlayTypesRepository playTypesRepository;

    @Test
    void findById() {
        /*
         * given
         */
        // music
        Music music = Music.builder().title("music").build();
        musicRepository.save(music);

        // playable
        Pattern pattern = Pattern.builder().level(2).music(music).build();
        patternRepository.save(pattern);

        // bpm
//        List<MusicBpmSaveEntityDto> bpm_datas = new ArrayList() {
//            { add(MusicBpmSaveEntityDto.builder().value(100).build());
//                add(MusicBpmSaveEntityDto.builder().value(150).build());}};
//        List<Bpm> bpms = new ArrayList<>();
//        bpms.add(bpm_datas.get(0).toEntity(playable));
//        bpms.add(bpm_datas.get(1).toEntity(playable));
//        bpmRepository.saveAll(bpms);
//        playable.add_bpms_for_save_request(bpms);


        /*
         * when
         */
        Optional<Pattern> p = patternRepository.findById(pattern.getId());
        pattern = p.get();

        /*
         * then
         */
        assertThat(pattern.getLevel()).isEqualTo(2);
        assertThat(pattern.getMusic().getTitle()).isEqualTo("music");
    }
}
