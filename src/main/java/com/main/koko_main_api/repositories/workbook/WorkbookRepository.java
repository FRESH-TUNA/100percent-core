package com.main.koko_main_api.repositories.workbook;

import com.main.koko_main_api.domains.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;


/*
 * PlayableSearchRepository 에사 findbyuid, findall을 재정의 한다.
 */
public interface WorkbookRepository extends JpaRepository<Workbook, Long>, WorkbookSearchRepository<Workbook, Long> {

}
