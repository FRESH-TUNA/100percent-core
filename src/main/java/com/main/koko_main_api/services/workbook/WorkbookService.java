package com.main.koko_main_api.services.workbook;

import com.main.koko_main_api.assemblers.workbook.WorkbookAssembler;
import com.main.koko_main_api.assemblers.workbook.WorkbookDeassembler;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.Workbook;
import com.main.koko_main_api.dtos.workbook.WorkbookRequestDto;

import com.main.koko_main_api.dtos.workbook.WorkbookResponseDto;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import com.main.koko_main_api.repositories.workbook.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WorkbookService {
    private final WorkbookRepository workbookRepository;
    private final PatternRepository patternRepository;

    private final WorkbookDeassembler deassembler;
    private final WorkbookAssembler assembler;

    private final WorkbookServiceHelper helper;

    // create
    @Transactional
    public WorkbookResponseDto create(WorkbookRequestDto requestDto) {
        List<Long> pattern_ids = helper.urls_to_ids(requestDto.getPatterns());
        List<Pattern> patterns = patternRepository.findAllById(pattern_ids);
        Workbook workbook = deassembler.toEntity(requestDto, patterns);
        return assembler.toModel(workbookRepository.save(workbook), patterns);
    }

    public WorkbookResponseDto findById(Long id) {
        Workbook workbook = workbookRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        List<Pattern> patterns = patternRepository.findAll(helper.patterns(workbook));
        return assembler.toModel(workbookRepository.save(workbook), patterns);
    }
}

