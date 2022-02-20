package com.main.koko_main_api.services;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.dtos.music.MusicListDto;
import com.main.koko_main_api.dtos.music.MusicPlayablesDto;
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

    public Page<MusicListDto> findAll(Pageable pageable, MusicRequestParams params) {
        String filter = params.getFilter();
        if(filter.equals("difficulty")) return difficultyFindAll(pageable, params);
        return pagedfindAll(pageable, params);
    }

    private Page<MusicListDto> difficultyFindAll(Pageable pageable, MusicRequestParams params) {
        Long play_type_id = params.getPlay_type();
        Long difficulty_type_id = params.getDifficulty_type();
        List<Music> musics = musicRepository.findAll(params);
        List<MusicListDto> musicListDtos = new ArrayList<>();

        for(Music m : musics) {
            List<MusicPlayablesDto> filtered_playables = new ArrayList<>();
            List<Playable> playables = m.getPlayables();

            // play_type, difficulty filtering
            if(play_type_id != null) {
                for(Playable playable : playables) {
                    if(playable.getPlayType().getId().equals(play_type_id) &&
                        playable.getDifficultyType().getId().equals(difficulty_type_id)) {
                        filtered_playables.add(new MusicPlayablesDto(playable));
                    }
                }
            }
            else {
                for(Playable playable : playables) {
                    if(playable.getDifficultyType().getId().equals(difficulty_type_id)) {
                        filtered_playables.add(new MusicPlayablesDto(playable));
                    }
                }
            }

            if(!filtered_playables.isEmpty())
                musicListDtos.add(new MusicListDto(m, filtered_playables));
        }

        return new PageImpl<>(musicListDtos, pageable, musicListDtos.size());
    }

    private Page<MusicListDto> pagedfindAll(Pageable pageable, MusicRequestParams params) {
        Long play_type_id = params.getPlay_type();
        List<Music> musics = musicRepository.findAll(pageable, params).getContent();

        List<MusicListDto> musicListDtos = new ArrayList<>();
        for(Music m : musics) {
            List<MusicPlayablesDto> filtered_playables = new ArrayList<>();
            List<Playable> playables = m.getPlayables();

            // play_type filtering
            if(play_type_id != null) {
                for(Playable playable : playables) {
                    if(playable.getPlayType().getId().equals(play_type_id)) {
                        filtered_playables.add(new MusicPlayablesDto(playable));
                    }
                }
            }
            else {
                for(Playable playable : playables) {
                    filtered_playables.add(new MusicPlayablesDto(playable));
                }
            }

            musicListDtos.add(new MusicListDto(m, filtered_playables));
        }

        return new PageImpl<>(musicListDtos, pageable, musicListDtos.size());
    }
}
