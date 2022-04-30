package com.main.koko_main_api.repositories.workbook;

import com.main.koko_main_api.domains.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
}
