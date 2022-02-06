package com.main.koko_main_api.domainDtos.playable.bpm;
import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.Playable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */
@NoArgsConstructor
@Getter
public class PlayableBpmSaveEntityDto {
    private Integer value;


    @Builder
    public PlayableBpmSaveEntityDto(Integer value) {
        this.value = value;
    }

    public Bpm toEntity(Playable playable) {
        return Bpm.builder()
                .value(value)
                .playable(playable)
                .build();
    }
}