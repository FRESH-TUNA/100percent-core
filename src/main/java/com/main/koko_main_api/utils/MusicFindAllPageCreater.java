package com.main.koko_main_api.utils;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class MusicFindAllPageCreater {
    public Page<Music> call(Page<Music> music_page, List<Pattern> patterns) {
        HashMap<Music, Music> entity_dto_mapper = new HashMap<>();
        Page<Music> result = music_page
                .map(m -> {
                    Music dto = Music.builder().id(m.getId())
                            .album(m.getAlbum()).title(m.getTitle()).build();
                    entity_dto_mapper.put(m, dto);
                    return dto;
                });
        for(Pattern p: patterns) entity_dto_mapper.get(p.getMusic()).add_pattern(p);
        return result;
    }
}
