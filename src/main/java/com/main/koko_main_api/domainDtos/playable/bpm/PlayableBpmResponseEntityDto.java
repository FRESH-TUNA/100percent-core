package com.main.koko_main_api.domainDtos.playable.bpm;

import com.main.koko_main_api.domains.Bpm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PlayableBpmResponseEntityDto {
    private Long id;
    private Integer value;

    public PlayableBpmResponseEntityDto(Bpm entity) {
        this.id = entity.getId();
        this.value = entity.getValue();
    }
}
