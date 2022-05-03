package com.main.koko_main_api.repositories.workbook;

import com.main.koko_main_api.domains.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkbookSearchRepository<T, ID> {
    Optional<Workbook> findById(ID id);

    Page<Workbook> findAll(Pageable pageable);

    Page<Workbook> findAll(Pageable pageable, Long play_type_id);
}
