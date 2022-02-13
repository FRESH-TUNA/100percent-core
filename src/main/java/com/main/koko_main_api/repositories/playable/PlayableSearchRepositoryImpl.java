package com.main.koko_main_api.repositories.playable;

import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.domains.QPlayable;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
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
public class PlayableSearchRepositoryImpl
        extends QuerydslRepositorySupport implements PlayableSearchRepository<Playable, Long> {

    private final JPAQueryFactory queryFactory;

    public PlayableSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Playable.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Playable> findAll() {
        QPlayable playable = QPlayable.playable;
        List<Playable> playables = queryFactory
                .selectDistinct(playable).from(playable)
                .leftJoin(playable.music).fetchJoin()
                .leftJoin(playable.bpms).fetchJoin()
                .leftJoin(playable.playType).fetchJoin()
                .leftJoin(playable.difficultyType).fetchJoin()
                .fetch();

        return playables;
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
}
