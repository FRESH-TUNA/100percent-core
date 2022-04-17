package com.main.koko_main_api.services.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicFindAllHelper {
    public Page<Music> create(Page<Music> music_page, List<Pattern> patterns) {
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

    public List<Music> getMusicsFromPatterns(List<Pattern> patterns) {
        return patterns.stream().map(p -> p.getMusic())
                .distinct().sorted(Comparator.comparingLong(x -> x.getId()))
                .collect(Collectors.toList());
    }

    public Page<Music> musicsToSinglePage(List<Music> musics) {
        return new PageImpl(musics, PageRequest.of(0, musics.size()), musics.size());
    }
}
