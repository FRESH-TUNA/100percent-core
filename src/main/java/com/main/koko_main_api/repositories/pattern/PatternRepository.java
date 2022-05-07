package com.main.koko_main_api.repositories.pattern;

import com.main.koko_main_api.domains.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;


/*
 * PlayableSearchRepository 에사 findbyuid, findall을 재정의 한다.
 */
public interface PatternRepository extends JpaRepository<Pattern, Long>, PatternSearchRepository<Pattern, Long> {

}
