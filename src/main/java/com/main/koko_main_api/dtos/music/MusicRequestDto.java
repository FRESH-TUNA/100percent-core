package com.main.koko_main_api.dtos.music;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

// https://stackoverflow.com/questions/37186417/resolving-entity-uri-in-custom-controller-spring-hateoas

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MusicRequestDto {
    private URI music;
    private String title;
    private URI album;

    private List<URI> composers;
    private Integer min_bpm, max_bpm;
}