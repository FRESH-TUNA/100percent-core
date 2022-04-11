package com.main.koko_main_api.repositories.pattern;

import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.QPattern;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

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
    private BooleanExpression music_eq(List<Music> musics) {
        return QPattern.pattern.music.in(musics);
    }

    private BooleanExpression play_type_id_eq(Long id) {
        return id != null ? QPattern.pattern.playType.id.eq(id) : null;
    }

    private BooleanExpression difficulty_id_eq(Long id) {
        return id != null ? QPattern.pattern.difficultyType.id.eq(id) : null;
    }

    private BooleanExpression levelEq(Integer level) {
        return level != null ? QPattern.pattern.level.eq(level) : null;
    }

    /*
     * main methods
     */
    public List<Pattern> findAllByPlayTypeAndMusics(List<Music> musics, Long play_type_id) {
        QPattern pattern = QPattern.pattern;
        return queryFactory
                .select(pattern)
                .from(pattern)
                .innerJoin(pattern.difficultyType).fetchJoin()
                .innerJoin(pattern.playType).fetchJoin()
                .where(music_eq(musics), play_type_id_eq(play_type_id))
                .fetch();
    }

    @Override
    public List<Pattern> findAllByPlayTypeAndDifficulty(Long play_type_id, Long difficulty_id) {
        QPattern pattern = QPattern.pattern;
        return queryFactory
                .select(pattern)
                .from(pattern)
                .innerJoin(pattern.difficultyType).fetchJoin()
                .innerJoin(pattern.playType).fetchJoin()
                .where(difficulty_id_eq(difficulty_id), play_type_id_eq(play_type_id))
                .fetch();
    }

    @Override
    public List<Pattern> findAllByPlayTypeAndLevel(Long play_type_id, Integer level) {
        QPattern pattern = QPattern.pattern;
        return queryFactory
                .select(pattern)
                .from(pattern)
                .leftJoin(pattern.difficultyType).fetchJoin()
                .leftJoin(pattern.playType).fetchJoin()
                .where(levelEq(level), play_type_id_eq(play_type_id))
                .fetch();
    }
}
