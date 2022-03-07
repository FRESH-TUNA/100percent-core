package com.main.koko_main_api.repositories.pattern;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.QMusic;
import com.main.koko_main_api.domains.QPattern;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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
public class PatternSearchRepositoryImpl
        extends QuerydslRepositorySupport
        implements PatternSearchRepository<Pattern, Long> {

    private final JPAQueryFactory queryFactory;

    public PatternSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Pattern.class);
        this.queryFactory = queryFactory;
    }

    /*
     * filters
     */
    private BooleanExpression music_ids_eq(List<Long> music_ids) {
        return QPattern.pattern.music.id.in(music_ids);
    }

    private BooleanExpression play_type_id_eq(Long id) {
        return id != null ? QPattern.pattern.playType.id.eq(id) : null;
    }

    /*
     * main methods
     */
    public List<Pattern> findAllByPlayTypeAndMusics(List<Long> music_ids, Long play_type_id) {
        QPattern pattern = QPattern.pattern;
        return queryFactory
                .selectDistinct(pattern)
                .from(pattern)
                .leftJoin(pattern.music).fetchJoin()
                .where(music_ids_eq(music_ids), play_type_id_eq(play_type_id))
                .fetch();
    }
}
