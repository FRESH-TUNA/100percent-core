package com.main.koko_main_api.dtos.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;

// https://stackoverflow.com/questions/37186417/resolving-entity-uri-in-custom-controller-spring-hateoas

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PatternRequestDto {
    private Integer level;
    private URI music;
    private URI difficultyType;
    private URI playType;
}
