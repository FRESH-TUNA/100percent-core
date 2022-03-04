package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.dtos.music.bpm.MusicBpmsSavePayloadDto;
import lombok.AllArgsConstructor;

import lombok.Getter;

import java.net.URI;
import java.util.List;

// https://stackoverflow.com/questions/37186417/resolving-entity-uri-in-custom-controller-spring-hateoas

@AllArgsConstructor
@Getter
public class MusicSaveDto {
    private String title;
    private URI album;
    private List<URI> composers;
    private List<MusicBpmsSavePayloadDto> bpms;
}