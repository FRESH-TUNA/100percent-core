package com.main.koko_main_api.payloads.playable;

import com.main.koko_main_api.entityDtos.playable.bpm.BpmsSaveDto;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

// https://stackoverflow.com/questions/37186417/resolving-entity-uri-in-custom-controller-spring-hateoas

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayableSavePayload {
    private Integer level;
    private List<BpmsSaveDto> bpms;
    private URI music;
}