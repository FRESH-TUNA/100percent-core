package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.controllers.Playables.PlayableParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MusicSearchRepository<T, ID> {
    List<T> findAll();

    List<T> findAll(PlayableParams params);

    Page<T> findAll(Pageable pageable, PlayableParams params);

    Optional<T> findById(ID id);
}
