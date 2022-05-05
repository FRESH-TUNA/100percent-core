package com.main.koko_main_api.services.workbook;

import com.main.koko_main_api.assemblers.WorkbookAssembler;
import com.main.koko_main_api.deassemblers.WorkbookDeassembler;
import com.main.koko_main_api.assemblers.WorkbooksAssembler;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.Workbook;
import com.main.koko_main_api.dtos.workbook.WorkbookRequestDto;

import com.main.koko_main_api.dtos.workbook.WorkbookResponseDto;
import com.main.koko_main_api.dtos.workbook.WorkbooksResponseDto;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import com.main.koko_main_api.repositories.workbook.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WorkbookService {
    private final WorkbookRepository workbookRepository;
    private final PatternRepository patternRepository;

    private final WorkbookDeassembler deassembler;
    private final WorkbookAssembler assembler;
    private final WorkbooksAssembler sassembler;
    private final PagedResourcesAssembler<Workbook> pageAssembler;

    private final WorkbookServiceHelper helper;


    public PagedModel<WorkbooksResponseDto> findAll(Pageable pageable, Long play_type_id) {
        Page<Workbook> page = workbookRepository.findAll(pageable, play_type_id);
        return pageAssembler.toModel(page, sassembler);
    }

    public PagedModel<WorkbooksResponseDto> findAll(Pageable pageable) {
        Page<Workbook> page = workbookRepository.findAll(pageable);
        return pageAssembler.toModel(page, sassembler);
    }

    /*
     * create
     */
    @Transactional
    public WorkbookResponseDto create(WorkbookRequestDto requestDto) {
        List<Long> pattern_ids = helper.urls_to_ids(requestDto.getPatterns());
        List<Pattern> patterns = patternRepository.findAllById(pattern_ids);
        Workbook workbook = deassembler.toEntity(requestDto, patterns);
        return assembler.toModel(workbookRepository.save(workbook), patterns);
    }


    /*
     * find by id
     */
    public WorkbookResponseDto findById(Long id) {
        Workbook workbook = workbookRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        List<Pattern> patterns = patternRepository.findAll(helper.patterns(workbook));
        return assembler.toModel(workbookRepository.save(workbook), patterns);
    }

    /*
     * update
     */
    @Transactional
    public WorkbookResponseDto update(Long id, WorkbookRequestDto requestDto) {
        List<Long> pattern_ids = helper.urls_to_ids(requestDto.getPatterns());
        List<Pattern> patterns = patternRepository.findAllById(pattern_ids);

        Workbook new_workbook = deassembler.toEntity(requestDto, patterns);
        Workbook workbook = workbookRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        workbook.update(new_workbook);
        return assembler.toModel(workbook, patterns);
    }

    /*
     * delete
     */
    public void delete(Long id) {
        workbookRepository.deleteById(id);
    }
}

