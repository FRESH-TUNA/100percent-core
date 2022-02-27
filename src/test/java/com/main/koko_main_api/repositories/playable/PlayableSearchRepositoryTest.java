package com.main.koko_main_api.repositories.playable;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.controllers.playable.PlayableParams;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.PlayType;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.repositories.BpmRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({PlayableSearchRepositoryImpl.class, RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayableSearchRepositoryTest {
    @Autowired
    private PlayableRepository playableRepository;

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
        Playable playable = Playable.builder().level(2).music(music).build();
        playableRepository.save(playable);

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
        Optional<Playable> p = playableRepository.findById(playable.getId());
        playable = p.get();

        /*
         * then
         */
        assertThat(playable.getLevel()).isEqualTo(2);
        assertThat(playable.getMusic().getTitle()).isEqualTo("music");
    }

    @Test
    void findAll_filter_by_PlayType() {
        /*
         * given
         */
        final int FIVE_KEY_N_PLAYABLES = 2;
        final int SIX_KEY_N_PLAYABLES = 3;

        // music
        Music music = Music.builder().title("music").build();
        musicRepository.save(music);

        // playType
        PlayType FIVE_KEY_playType = PlayType.builder().title("5K").build();
        PlayType SIX_KEY_playType = PlayType.builder().title("6K").build();
        playTypesRepository.save(FIVE_KEY_playType);
        playTypesRepository.save(SIX_KEY_playType);

        // playable
        List<Playable> playables = new ArrayList<>();
        for(int i = 0; i < FIVE_KEY_N_PLAYABLES; ++i) {
            playables.add(Playable.builder()
                    .level(2).music(music).playType(FIVE_KEY_playType).build());
        }
        for(int i = 0; i < SIX_KEY_N_PLAYABLES; ++i) {
            playables.add(Playable.builder()
                    .level(2).playType(SIX_KEY_playType).music(music).build());
        }
        playableRepository.saveAll(playables);

//        // bpms
//        for(Playable p : playables) {
//            List<MusicBpmSaveEntityDto> bpm_datas = new ArrayList() {
//                { add(MusicBpmSaveEntityDto.builder().value(100).build());
//                    add(MusicBpmSaveEntityDto.builder().value(150).build());}};
//            List<Bpm> bpms = bpm_datas.stream().map(
//                    bpm -> bpm.toEntity(p)).collect(Collectors.toList());
//            bpmRepository.saveAll(bpms);
//            p.add_bpms_for_save_request(bpms);
//        }

        /*
         * when
         */
        PlayableParams FIVE_KEY_playType_params = PlayableParams.builder()
                .play_type(FIVE_KEY_playType.getId()).build();
        PlayableParams SIX_KEY_playType_params = PlayableParams.builder()
                .play_type(SIX_KEY_playType.getId()).build();
        List<Playable> FIVE_KEY_playType_playables = playableRepository.findAll(FIVE_KEY_playType_params);
        List<Playable> SIX_KEY_playType_playables = playableRepository.findAll(SIX_KEY_playType_params);

        /*
         * then
         */
        assertThat(FIVE_KEY_playType_playables.size()).isEqualTo(FIVE_KEY_playType_playables.size());
        assertThat(SIX_KEY_playType_playables.size()).isEqualTo(SIX_KEY_playType_playables.size());
    }

    @Test
    void findAll() {
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

//        for(Playable p : playables) {
//            List<MusicBpmSaveEntityDto> bpm_datas = new ArrayList() {
//                { add(MusicBpmSaveEntityDto.builder().value(100).build());
//                    add(MusicBpmSaveEntityDto.builder().value(150).build());}};
//            List<Bpm> bpms = bpm_datas.stream().map(
//                    bpm -> bpm.toEntity(p)).collect(Collectors.toList());
//            bpmRepository.saveAll(bpms);
//            p.add_bpms_for_save_request(bpms);
//        }

        /*
         * when
         */
        playables = playableRepository.findAll();

        /*
         * then
         */
        assertThat(playables.size()).isEqualTo(N_PLAYABLES);
        assertThat(playables.get(0).getLevel()).isEqualTo(2);
        assertThat(playables.get(0).getMusic().getTitle()).isEqualTo("music");
    }
}
