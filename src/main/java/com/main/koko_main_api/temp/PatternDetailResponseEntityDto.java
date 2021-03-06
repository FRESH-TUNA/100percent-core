package com.main.koko_main_api.temp;

import com.main.koko_main_api.domains.Pattern;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PatternDetailResponseEntityDto {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PatternDetailResponseEntityDto(Pattern p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
    }
}
