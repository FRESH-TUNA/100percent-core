package com.main.koko_main_api.Dtos;

import com.main.koko_main_api.Models.Album;
import com.main.koko_main_api.Models.Bpm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class BpmsResponseDto {
    private Long id;
    private Integer value;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BpmsResponseDto(Bpm entity) {
        this.id = entity.getId();
        this.value = entity.getValue();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
