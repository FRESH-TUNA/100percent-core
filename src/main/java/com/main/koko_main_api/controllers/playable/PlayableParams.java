package com.main.koko_main_api.controllers.playable;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlayableParams {
    private Long play_type;
    private Long difficulty_type;

    @Override
    public String toString(){
        return "PlayableParams [play_type = " + play_type + "]";
    }

    @Builder
    public PlayableParams(Long play_type, Long difficulty_type) {

        this.play_type = play_type;
        this.difficulty_type = difficulty_type;
    }
}
