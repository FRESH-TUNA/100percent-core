package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.dtos.playable.bpm.PlayableBpmSaveEntityDto;
import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.PlayType;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.repositories.BpmRepository;
import com.main.koko_main_api.repositories.PlayTypesRepository;
import com.main.koko_main_api.repositories.playable.PlayableRepository;

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

@DataJpaTest
@Import({MusicSearchRepositoryImpl.class, RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MusicSearchRepositoryTest {
    @Autowired
    private PlayableRepository playableRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private BpmRepository bpmRepository;

    @Autowired
    private PlayTypesRepository playTypesRepository;

    @Test
    void findAll_test() {
        /*
         * given
         */
        final int N_PLAYABLES = 5;

        PlayType playType = PlayType.builder().title("5K").build();
        playTypesRepository.save(playType);

        Music music = Music.builder().title("music").build();
        musicRepository.save(music);

        List<Playable> playables = new ArrayList<>();
        for(int i = 0; i < N_PLAYABLES; ++i) {
            playables.add(Playable.builder()
                    .level(2).music(music).build());
        }
        playableRepository.saveAll(playables);

        for(Playable p : playables) {
            List<PlayableBpmSaveEntityDto> bpm_datas = new ArrayList() {
                { add(PlayableBpmSaveEntityDto.builder().value(100).build());
                    add(PlayableBpmSaveEntityDto.builder().value(150).build());}};
            List<Bpm> bpms = bpm_datas.stream().map(
                    bpm -> bpm.toEntity(p)).collect(Collectors.toList());
            bpmRepository.saveAll(bpms);
            p.add_bpms_for_save_request(bpms);
            music.add_playable(p);
        }

        /*
         * when
         */
        List<Music> musics = musicRepository.findAll();

        /*
         * then
         */
        playables = musics.get(0).getPlayables();
        assertThat(playables.size()).isEqualTo(N_PLAYABLES);
        assertThat(playables.get(0).getLevel()).isEqualTo(2);
        assertThat(playables.get(0).getMusic().getTitle()).isEqualTo("music");
        assertThat(playables.get(0).getBpms().size()).isEqualTo(2);
        assertThat(playables.get(0).getBpms().get(0).getValue()).isEqualTo(100);
    }
}
