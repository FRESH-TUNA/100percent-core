package com.main.koko_main_api.Services;

import com.main.koko_main_api.Dtos.BpmsSaveDto;
import com.main.koko_main_api.Dtos.PlayablesResponseDto;
import com.main.koko_main_api.Dtos.PlayablesSaveDto;
import com.main.koko_main_api.Models.Bpm;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlayablesServiceTest {

    @Autowired
    private PlayablesService playablesService;

//    @Test
//    public void save_test() {
//        Set<Bpm> bpms = new HashSet() {
//            { add(BpmsSaveDto.builder().value(100).build().toEntity()); }};
//        PlayablesSaveDto dto = PlayablesSaveDto.builder()
//                .level(2)
//                .bpms(bpms)
//                .build();
//        PlayablesResponseDto responseDto = playablesService.save(dto);
//
//
//        // 검증
//        assertThat(responseDto.getLevel()).isEqualTo(2);
//        Iterator<Bpm> it = responseDto.getBpms().iterator();
//        assertThat(it.next().getValue()).isEqualTo(100);
//    }
}
