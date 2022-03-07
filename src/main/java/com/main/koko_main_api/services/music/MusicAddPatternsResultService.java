package com.main.koko_main_api.services.music;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.music.MusicEntityToServiceDto;
import com.main.koko_main_api.dtos.music.patterns.MusicPatternsDto;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@NoArgsConstructor
public class MusicAddPatternsResultService {
    public Page<MusicEntityToServiceDto> call(Page<Music> music_page,
                                              List<Pattern> patterns) {
        HashMap<Music, MusicEntityToServiceDto> entity_dto_mapper = new HashMap<>();
        Page<MusicEntityToServiceDto> result = music_page
                .map(m -> {
                    MusicEntityToServiceDto dto = new MusicEntityToServiceDto(m);
                    entity_dto_mapper.put(m, dto);
                    return dto;
                });

        for(Pattern p: patterns) {
            entity_dto_mapper.get(p.getMusic()).addMusicPatternDto(new MusicPatternsDto(p));
        }

        return result;
    }
}
