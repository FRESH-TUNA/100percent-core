package com.main.koko_main_api.repositories.playable;

import com.main.koko_main_api.domains.Playable;
import org.springframework.data.jpa.repository.JpaRepository;


/*
 * PlayableSearchRepository 에사 findbyuid, findall을 재정의 한다.
 */
public interface PlayableRepository extends JpaRepository<Playable, Long>, PlayableSearchRepository<Playable, Long> {

}
