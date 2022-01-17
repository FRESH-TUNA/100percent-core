package com.main.koko_main_api.Dtos;
import com.main.koko_main_api.Models.Bpm;
import com.main.koko_main_api.Models.Playable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */
@NoArgsConstructor
@Getter
public class PlayablesSaveDto {
    private Integer level;
    private Set<Bpm> bpms;

    @Builder
    public PlayablesSaveDto(Integer level, Set<Bpm> bpms) {
        this.level = level;
        this.bpms = bpms;
    }

    public Playable toEntity() {
        return Playable.builder()
                .level(this.level)
                .bpms(this.bpms)
                .build();
    }
}
