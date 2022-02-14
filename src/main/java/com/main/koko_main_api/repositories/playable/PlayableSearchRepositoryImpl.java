package com.main.koko_main_api.repositories.playable;

import com.main.koko_main_api.controllers.Playables.PlayableParams;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.domains.QPlayable;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * 참고자료
 * https://velog.io/@max9106/JPA-QueryDSL
 * https://joanne.tistory.com/270
 * https://jojoldu.tistory.com/372
 */
@Repository
public class PlayableSearchRepositoryImpl implements PlayableSearchRepository<Playable, Long> {

    private final JPAQueryFactory queryFactory;

    public PlayableSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Playable> findAll() {
        return findAll_base_query().fetch();
    }

    public List<Playable> findAll(PlayableParams params) {
        return findAll_base_query()
                .where(
                        playTypeEq(params.getPlay_type())
                )
                .fetch();
    }

    private JPAQuery<Playable> findAll_base_query() {
        QPlayable playable = QPlayable.playable;
        return queryFactory
                .selectDistinct(playable).from(playable)
                .leftJoin(playable.music).fetchJoin()
                .leftJoin(playable.bpms).fetchJoin()
                .leftJoin(playable.playType).fetchJoin()
                .leftJoin(playable.difficultyType).fetchJoin();
    }

    @Override
    public Page<Playable> findAll(Pageable pageable, Predicate predicate) {
        return null;
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

    private BooleanExpression playTypeEq(Long id) {
        return id != null ? QPlayable.playable.playType.id.eq(id);
    }
}
