package com.main.koko_main_api.Dtos;

import com.main.koko_main_api.Models.Bpm;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.Set;

// https://stackoverflow.com/questions/37186417/resolving-entity-uri-in-custom-controller-spring-hateoas

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayablesSaveRequestDto{
    private Integer level;
    private Set<Bpm> bpms;
    private URI music;
}
