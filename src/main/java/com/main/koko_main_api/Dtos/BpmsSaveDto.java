package com.main.koko_main_api.Dtos;
import com.main.koko_main_api.Models.Bpm;
import com.main.koko_main_api.Models.Playable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */
@NoArgsConstructor
@Getter
public class BpmsSaveDto {
    private Integer value;


    @Builder
    public BpmsSaveDto(Integer value) {
        this.value = value;
    }

    public Bpm toEntity(Playable playable) {
        return Bpm.builder()
                .value(value)
                .playable(playable)
                .build();
    }
}