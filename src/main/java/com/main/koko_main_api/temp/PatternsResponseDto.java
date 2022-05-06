package com.main.koko_main_api.temp;

import lombok.Getter;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;


/*
 * @Relation 읉 통해 page로 변환할때 _embedded naming을 바꿀수있다.
 */
@Getter
@Relation(collectionRelation = "playables")
public class PatternsResponseDto extends RepresentationModel<PatternsResponseDto> {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
