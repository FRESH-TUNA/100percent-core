package com.main.koko_main_api.entityDtos.playable.bpm;

import com.main.koko_main_api.domains.Bpm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BpmsResponseDto {
    private Long id;
    private Integer value;

    public BpmsResponseDto(Bpm entity) {
        this.id = entity.getId();
        this.value = entity.getValue();
    }
}
