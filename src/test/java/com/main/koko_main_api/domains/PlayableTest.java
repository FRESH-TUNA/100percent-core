package com.main.koko_main_api.domains;

import com.main.koko_main_api.entityDtos.playable.bpm.BpmsSaveDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayableTest {

    @Test
    void add_bpms_for_save_request() {
        /*
         * data mocking
         */
        Playable playable = new Playable();

        List<BpmsSaveDto> BpmSaveDtos = new ArrayList() {
            { add(BpmsSaveDto.builder().value(100).build());
              add(BpmsSaveDto.builder().value(150).build()); }};

        List<Bpm> bpms = BpmSaveDtos.stream().map(
                bpm -> bpm.toEntity(playable)).collect(Collectors.toList());

        /*
         * when
         */
        playable.add_bpms_for_save_request(bpms);

        //then
        List<Bpm> saved_bpms = playable.getBpms();
        assertThat(saved_bpms.get(0).getValue()).isEqualTo(100);
        assertThat(saved_bpms.get(1).getValue()).isEqualTo(150);
    }

//    @Autowired
//    private PlayablesRepository pr;
//
//    @Autowired
//    private BpmsRepository br;

//    @Test
//    public void playable_to_bpm_test() {
//        Playable play = PlayableSaveEntityDto.builder().level(2).build().toEntity();
//        play = pr.save(play);
//
//        /*bpm*/
//        Bpm bpm = BpmsSaveDto.builder()
//                .value(100)
//                .build()
//                .toEntity(play);
//        br.save(bpm);
//
//        //tests
//        play = pr.findById(play.getId()).get();
//        Iterator<Bpm> it = play.getBpms().iterator();
//        assertThat(it.next().getValue()).isEqualTo(100);
//    }
}
