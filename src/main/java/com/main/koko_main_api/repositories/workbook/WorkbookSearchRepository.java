package com.main.koko_main_api.repositories.workbook;

import com.main.koko_main_api.domains.Workbook;

import java.util.Optional;

public interface WorkbookSearchRepository<T, ID> {
    Optional<Workbook> findById(ID id);
}
