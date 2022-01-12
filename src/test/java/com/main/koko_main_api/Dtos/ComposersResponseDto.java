package com.main.koko_main_api.Dtos;
import com.main.koko_main_api.Models.Composer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ComposersResponseDto {
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ComposersResponseDto(Composer entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
