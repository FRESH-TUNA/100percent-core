package com.main.koko_main_api.dtos.music.bpm;
import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.Music;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */
@NoArgsConstructor
@Getter
public class MusicBpmSaveEntityDto {
    private Integer value;

    @Builder
    public MusicBpmSaveEntityDto(Integer value) {
        this.value = value;
    }

    public Bpm toEntity(Music music) {
        return Bpm.builder()
                .value(value)
                .music(music)
                .build();
    }
}