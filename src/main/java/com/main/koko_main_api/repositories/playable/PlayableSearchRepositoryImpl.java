package com.main.koko_main_api.repositories.playable;

import com.main.koko_main_api.controllers.playable.PlayableParams;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.domains.QPlayable;
import com.querydsl.core.types.dsl.BooleanExpression;
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
public class PlayableSearchRepositoryImpl
        extends QuerydslRepositorySupport
        implements PlayableSearchRepository<Playable, Long> {

    private final JPAQueryFactory queryFactory;

    public PlayableSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Playable.class);
        this.queryFactory = queryFactory;
    }

    /*
     * helper methods
     */
    private JPAQuery<Playable> findAll_base_query() {
        QPlayable playable = QPlayable.playable;
        return queryFactory
                .selectDistinct(playable).from(playable)
                .leftJoin(playable.music).fetchJoin()
                .leftJoin(playable.bpms).fetchJoin()
                .leftJoin(playable.playType).fetchJoin()
                .leftJoin(playable.difficultyType).fetchJoin();
    }

    private JPAQuery<Playable> findAll_filter_apply(JPAQuery<Playable> findAll_base, PlayableParams params) {
        return findAll_base
                .where(
                        playTypeEq(params.getPlay_type()),
                        difficultyTypeEq(params.getDifficulty_type())
                );
    }

    /*
     * main methods
     */
    @Override
    public List<Playable> findAll() {
        return findAll_base_query().fetch();
    }

    @Override
    public List<Playable> findAll(PlayableParams params) {
        return findAll_filter_apply(findAll_base_query(), params)
                .fetch();
    }

    @Override
    public Page<Playable> findAll(Pageable pageable, PlayableParams params) {
        List<Playable> playables = getQuerydsl().applyPagination(
                pageable,
                findAll_filter_apply(findAll_base_query(), params)
        ).fetch();

        return new PageImpl<>(playables, pageable, playables.size());
    }

    @Override
    public Optional<Playable> findById(Long id) {
        QPlayable playable = QPlayable.playable;
        List<Playable> playables = queryFactory
                .selectDistinct(playable)
                .from(playable).where(playable.id.eq(id))
                .leftJoin(playable.music).fetchJoin()
                .leftJoin(playable.bpms).fetchJoin()
                .leftJoin(playable.playType).fetchJoin()
                .leftJoin(playable.difficultyType).fetchJoin()
                .fetch();

        if(playables.size() == 0) return Optional.empty();
        else return Optional.of(playables.get(0));
    }

    /*
     * filters
     */
    private BooleanExpression playTypeEq(Long id) {
        return id != null ? QPlayable.playable.playType.id.eq(id) : null;
    }
    private BooleanExpression difficultyTypeEq(Long id) {
        return id != null ? QPlayable.playable.difficultyType.id.eq(id) : null;
    }
}
