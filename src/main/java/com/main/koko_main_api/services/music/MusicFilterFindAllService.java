package com.main.koko_main_api.services.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.music.MusicEntityToServiceDto;
import com.main.koko_main_api.dtos.music.playables.MusicPlayablesDto;
import com.main.koko_main_api.repositories.music.MusicRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicFilterFindAllService {
    private final MusicRepository musicRepository;

    public List<MusicEntityToServiceDto> call(MusicRequestParams params) {
        String filter = params.getFilter();
        if(filter.equals("difficulty")) return difficultyFindAll(params);
        if(filter.equals("level")) return levelFindAll(params);

        return difficultyFindAll(params);
    }

    /*
     * playable/playtype + difficulty 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */
    private List<MusicEntityToServiceDto> difficultyFindAll(MusicRequestParams params) {
        Long play_type_id = params.getPlay_type();
        Long difficulty_type_id = params.getDifficulty_type();
        List<Music> musics = musicRepository.findAll();
        List<MusicEntityToServiceDto> musicEntityToServiceDtos = new ArrayList<>();

        for(Music m : musics) {
            List<MusicPlayablesDto> filtered_playables = new ArrayList<>();
            List<Pattern> patterns = m.getPatterns();

            // play_type, difficulty filtering
            for(Pattern pattern : patterns) {
                if(pattern.getPlayType().getId().equals(play_type_id) &&
                        pattern.getDifficultyType().getId().equals(difficulty_type_id)) {
                    filtered_playables.add(new MusicPlayablesDto(pattern));
                }
            }

            if(!filtered_playables.isEmpty())
                musicEntityToServiceDtos.add(new MusicEntityToServiceDto(m, filtered_playables));
        }

        return musicEntityToServiceDtos;
    }

    /*
     * playable/playtype + level 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */
    private List<MusicEntityToServiceDto> levelFindAll(MusicRequestParams params) {
        Long play_type_id = params.getPlay_type();
        Integer level = params.getLevel();
        List<Music> musics = musicRepository.findAll();
        List<MusicEntityToServiceDto> musicEntityToServiceDtos = new ArrayList<>();

        for(Music m : musics) {
            List<MusicPlayablesDto> filtered_playables = new ArrayList<>();
            List<Pattern> patterns = m.getPatterns();

            // play_type, difficulty filtering
            for(Pattern pattern : patterns) {
                if(pattern.getPlayType().getId().equals(play_type_id) &&
                        pattern.getLevel().equals(level)) {
                    filtered_playables.add(new MusicPlayablesDto(pattern));
                }
            }

            if(!filtered_playables.isEmpty())
                musicEntityToServiceDtos.add(new MusicEntityToServiceDto(m, filtered_playables));
        }

        return musicEntityToServiceDtos;
    }
}
