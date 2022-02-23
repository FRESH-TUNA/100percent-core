package com.main.koko_main_api.payloads.playable;

import com.main.koko_main_api.dtos.playable.PlayableListResponseEntityDto;
import com.main.koko_main_api.dtos.music.MusicBpmEntityDto;
import com.main.koko_main_api.dtos.playable.PlayableMusicEntityDto;
import lombok.Getter;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.List;


/*
 * @Relation 읉 통해 page로 변환할때 _embedded naming을 바꿀수있다.
 */
@Getter
@Relation(collectionRelation = "playables")
public class PlayableListPayload extends RepresentationModel<PlayableListPayload> {
    private Long id;
    private Integer level;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private PlayableMusicEntityDto music;

    public PlayableListPayload(PlayableListResponseEntityDto p)  {
        id = p.getId();
        level = p.getLevel();
        createdDate = p.getCreatedDate();
        modifiedDate = p.getModifiedDate();
        music = p.getMusic();
    }
}
