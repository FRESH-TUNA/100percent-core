package com.main.koko_main_api.services;

import com.main.koko_main_api.domains.Workbook;
import com.main.koko_main_api.dtos.workbook.WorkbookRequestDto;
import com.main.koko_main_api.dtos.workbook.WorkbooksResponseDto;
import com.main.koko_main_api.repositories.workbook.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;


@Service
@RequiredArgsConstructor
public class WorkbookService {
    private final WorkbookRepository workbookRepository;

    public PagedModel<WorkbooksResponseDto> findAll(Pageable pageable) {
        return workbookRepository.findAll(pageable);
    }

    // create
    @Transactional
    public Workbook create(WorkbookRequestDto requestDto) {
        //Hyper Fiber World Spectrum
        //The Bass Kills You
        //Toki
        //NORMAL
        //puru / kkkfff2

    }
}
