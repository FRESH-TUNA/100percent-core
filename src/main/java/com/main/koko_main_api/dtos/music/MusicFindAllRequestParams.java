package com.main.koko_main_api.dtos.music;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MusicFindAllRequestParams {
    private String mode;
    private Long play_type;
    private Long difficulty_type;
    private Long album;
    private Integer level;

    @Override
    public String toString(){
        return "PlayableParams [play_type = " + play_type + "]";
    }

    @Builder
    public MusicFindAllRequestParams(String mode, Long play_type, Long difficulty_type, Long album, Integer level) {
        this.mode = mode;
        this.play_type = play_type;
        this.difficulty_type = difficulty_type;
        this.album = album;
        this.level = level;
    }
}