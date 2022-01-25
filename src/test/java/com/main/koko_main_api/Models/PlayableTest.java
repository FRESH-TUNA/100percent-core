package com.main.koko_main_api.Models;

import com.main.koko_main_api.Dtos.BpmsSaveDto;
import com.main.koko_main_api.Dtos.PlayablesSaveDto;
import com.main.koko_main_api.Repositories.PlayablesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlayableTest {

    @Autowired
    private PlayablesRepository pr;

//    @Test
//    public void playable_add_bpm_test() {
//        Playable play = PlayablesSaveDto.builder().level(2).build().toEntity();
//        play.saveBpm(BpmsSaveDto.builder().value(100).build().toEntity());
//        Iterator<Bpm> it = pr.save(play).getBpms().iterator();
//
//        assertThat(it.next().getValue()).isEqualTo(100);
//    }
}
