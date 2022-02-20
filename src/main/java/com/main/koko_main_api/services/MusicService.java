package com.main.koko_main_api.services;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.dtos.music.MusicListResponseEntityDto;
import com.main.koko_main_api.dtos.music.MusicPlayablesResponseEntityDto;
import com.main.koko_main_api.dtos.playable.PlayableListResponseEntityDto;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.repositories.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;

    public Page<MusicListResponseEntityDto> findAll(Pageable pageable, MusicRequestParams params) {
        String filter = params.getFilter();
        if(filter.equals("difficulty")) return difficultyFindAll(pageable, params);
        return pagedfindAll(pageable, params);
    }

    private Page<MusicListResponseEntityDto> difficultyFindAll(Pageable pageable, MusicRequestParams params) {
        Long play_type_id = params.getPlay_type();
        Long difficulty_type_id = params.getDifficulty_type();
        List<Music> musics = musicRepository.findAll(params);
        List<MusicListResponseEntityDto> musicListResponseEntityDtos = new ArrayList<>();

        for(Music m : musics) {
            List<MusicPlayablesResponseEntityDto> filtered_playables = new ArrayList<>();
            List<Playable> playables = m.getPlayables();

            // play_type, difficulty filtering
            if(play_type_id != null) {
                for(Playable playable : playables) {
                    if(playable.getPlayType().getId().equals(play_type_id) &&
                        playable.getDifficultyType().getId().equals(difficulty_type_id)) {
                        filtered_playables.add(new MusicPlayablesResponseEntityDto(playable));
                    }
                }
            }
            else {
                for(Playable playable : playables) {
                    if(playable.getDifficultyType().getId().equals(difficulty_type_id)) {
                        filtered_playables.add(new MusicPlayablesResponseEntityDto(playable));
                    }
                }
            }

            if(!filtered_playables.isEmpty())
                musicListResponseEntityDtos.add(new MusicListResponseEntityDto(m, filtered_playables));
        }

        return new PageImpl<>(musicListResponseEntityDtos, pageable, musicListResponseEntityDtos.size());
    }

    private Page<MusicListResponseEntityDto> pagedfindAll(Pageable pageable, MusicRequestParams params) {
        Long play_type_id = params.getPlay_type();
        List<Music> musics = musicRepository.findAll(pageable, params).getContent();

        List<MusicListResponseEntityDto> musicListResponseEntityDtos = new ArrayList<>();
        for(Music m : musics) {
            List<MusicPlayablesResponseEntityDto> filtered_playables = new ArrayList<>();
            List<Playable> playables = m.getPlayables();

            // play_type filtering
            if(play_type_id != null) {
                for(Playable playable : playables) {
                    if(playable.getPlayType().getId().equals(play_type_id)) {
                        filtered_playables.add(new MusicPlayablesResponseEntityDto(playable));
                    }
                }
            }
            else {
                for(Playable playable : playables) {
                    filtered_playables.add(new MusicPlayablesResponseEntityDto(playable));
                }
            }

            musicListResponseEntityDtos.add(new MusicListResponseEntityDto(m, filtered_playables));
        }

        return new PageImpl<>(musicListResponseEntityDtos, pageable, musicListResponseEntityDtos.size());
    }
}
