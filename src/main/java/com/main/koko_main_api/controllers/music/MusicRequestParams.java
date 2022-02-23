package com.main.koko_main_api.controllers.music;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MusicRequestParams {
    private String filter;
    private Long play_type;
    private Long difficulty_type;
    private Long album;

    @Override
    public String toString(){
        return "PlayableParams [play_type = " + play_type + "]";
    }

    @Builder
    public MusicRequestParams(String filter, Long play_type, Long difficulty_type, Long album) {
        this.filter = filter;
        this.play_type = play_type;
        this.difficulty_type = difficulty_type;
        this.album = album;
    }
}
