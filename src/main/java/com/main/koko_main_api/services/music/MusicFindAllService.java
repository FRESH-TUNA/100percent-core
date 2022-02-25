package com.main.koko_main_api.services.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.dtos.music.MusicListDto;
import com.main.koko_main_api.dtos.music.MusicPlayablesDto;
import com.main.koko_main_api.repositories.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicFindAllService {
    private final MusicRepository musicRepository;

    public Page<MusicListDto> call(Pageable pageable, MusicRequestParams params) {
        String filter = params.getFilter();
        if(filter.equals("difficulty")) return difficultyFindAll(params);
        else return pagedfindAll(pageable, params);
    }

    /*
     * playable/playtype + difficulty 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */
    private Page<MusicListDto> difficultyFindAll(MusicRequestParams params) {
        Long play_type_id = params.getPlay_type();
        Long difficulty_type_id = params.getDifficulty_type();
        List<Music> musics = musicRepository.findAll();
        List<MusicListDto> musicListDtos = new ArrayList<>();

        System.out.println(difficulty_type_id);

        for(Music m : musics) {
            List<MusicPlayablesDto> filtered_playables = new ArrayList<>();
            List<Playable> playables = m.getPlayables();

            // play_type, difficulty filtering
            for(Playable playable : playables) {
                if(playable.getPlayType().getId().equals(play_type_id) &&
                        playable.getDifficultyType().getId().equals(difficulty_type_id)) {
                    filtered_playables.add(new MusicPlayablesDto(playable));
                }
            }

            if(!filtered_playables.isEmpty())
                musicListDtos.add(new MusicListDto(m, filtered_playables));
        }

        int list_size = musicListDtos.size();
        return new PageImpl<>(musicListDtos, PageRequest.of(0, list_size), list_size);
    }

    /*
     * playable/playType + album 에따른 필터링을 지원하는 findall 메소드
     * difficulty 기준 필터링
     */
    private Page<MusicListDto> pagedfindAll(Pageable pageable, MusicRequestParams params) {
        Page<Music> musics_page = musicRepository.findAll(pageable, params.getAlbum());
        Long play_type_id = params.getPlay_type();
        List<Music> musics = musics_page.getContent();
        List<MusicListDto> musicListDtos = new ArrayList<>();

        for(Music m : musics) {
            List<MusicPlayablesDto> filtered_playables = new ArrayList<>();
            List<Playable> playables = m.getPlayables();

            // play_type filtering
            for(Playable playable : playables)
                if(playable.getPlayType().getId().equals(play_type_id))
                    filtered_playables.add(new MusicPlayablesDto(playable));

            musicListDtos.add(new MusicListDto(m, filtered_playables));
        }

        return new PageImpl<>(musicListDtos, pageable, musics_page.getTotalElements());
    }
}
