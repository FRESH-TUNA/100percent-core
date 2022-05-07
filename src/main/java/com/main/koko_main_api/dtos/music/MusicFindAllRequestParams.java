package com.main.koko_main_api.dtos.music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicFindAllRequestParams {
    private String mode;
    private Long play_type;

    private Long difficulty_type;
    private Long album;
    private Integer level;
    private String title;

    @Override
    public String toString(){
        return "PlayableParams [play_type = " + play_type + "]";
    }
}
