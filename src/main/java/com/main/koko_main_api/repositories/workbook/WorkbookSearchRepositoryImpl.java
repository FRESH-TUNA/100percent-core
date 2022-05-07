package com.main.koko_main_api.repositories.workbook;

import com.main.koko_main_api.domains.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

/*
 * 참고자료
 * https://velog.io/@max9106/JPA-QueryDSL
 * https://joanne.tistory.com/270
 * https://jojoldu.tistory.com/372
 * https://jessyt.tistory.com/55
 */

//@Repository
public class WorkbookSearchRepositoryImpl
        extends QuerydslRepositorySupport
        implements WorkbookSearchRepository<Workbook, Long> {

    private final JPAQueryFactory queryFactory;

    public WorkbookSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Workbook.class);
        this.queryFactory = queryFactory;
    }

    /*
     * main methods
     */
    @Override
    public Optional<Workbook> findById(Long id) {
        QWorkbook workbook = QWorkbook.workbook;

        return Optional.ofNullable(queryFactory
                .select(workbook)
                .from(workbook)
                .leftJoin(workbook.patterns).fetchJoin()
                .where().fetchOne());
    }

    @Override
    public Page<Workbook> findAll(Pageable pageable) {
        JPAQuery<Long> counts_query = counts_base_query();
        JPAQuery<Workbook> query = findAll_base_query();

        // count query all
        Long counts = counts_query.fetchOne();
        List<Workbook> workbooks = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(workbooks, pageable, counts);
    }

    @Override
    public Page<Workbook> findAll(Pageable pageable, Long play_type_id) {
        QWorkbook workbook = QWorkbook.workbook;
        JPAQuery<Long> counts_query = counts_base_query();
        JPAQuery<Workbook> query = findAll_base_query();

        counts_query = counts_query.where(workbook.playType.id.eq(play_type_id));
        query = query.where(workbook.playType.id.eq(play_type_id));

        // count query all
        Long counts = counts_query.fetchOne();
        List<Workbook> workbooks = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(workbooks, pageable, counts);
    }

    /*
     * helper
     */
    private JPAQuery<Workbook> findAll_base_query() {
        QWorkbook workbook = QWorkbook.workbook;
        return queryFactory
                .select(workbook)
                .from(workbook)
                .leftJoin(workbook.playType).fetchJoin();
    }

    private JPAQuery<Long> counts_base_query() {
        QWorkbook workbook = QWorkbook.workbook;
        return queryFactory.select(workbook.count()).from(workbook);
    }
}
