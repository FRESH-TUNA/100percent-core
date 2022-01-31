package com.main.koko_main_api.domains;

import com.main.koko_main_api.entityDtos.playable.PlayableSaveEntityDto;
import com.main.koko_main_api.entityDtos.playable.bpm.BpmsSaveDto;
import com.main.koko_main_api.repositories.BpmsRepository;
import com.main.koko_main_api.repositories.PlayablesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlayableTest {

    @Autowired
    private PlayablesRepository pr;

    @Autowired
    private BpmsRepository br;

    @Test
    public void playable_to_bpm_test() {
        Playable play = PlayableSaveEntityDto.builder().level(2).build().toEntity();
        play = pr.save(play);

        /*bpm*/
        Bpm bpm = BpmsSaveDto.builder()
                .value(100)
                .build()
                .toEntity(play);
        br.save(bpm);

        //tests
        play = pr.findById(play.getId()).get();
        Iterator<Bpm> it = play.getBpms().iterator();
        assertThat(it.next().getValue()).isEqualTo(100);
    }
}
