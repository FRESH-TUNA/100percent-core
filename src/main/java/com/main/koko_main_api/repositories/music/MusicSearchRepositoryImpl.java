package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.domains.Music;

import com.main.koko_main_api.domains.QMusic;
import com.main.koko_main_api.domains.QPlayable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * https://www.inflearn.com/questions/15876
 * https://stackoverflow.com/questions/60071473/querydsl-filter-on-left-join-with-subquery
 */

public class MusicSearchRepositoryImpl
        extends QuerydslRepositorySupport
        implements MusicSearchRepository<Music, Long> {

    private final JPAQueryFactory queryFactory;

    public MusicSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Music.class);
        this.queryFactory = queryFactory;
    }

    /*
     * filters
     */
    private BooleanExpression albumEq(Long id) {
        return id != null ? QMusic.music.album.id.eq(id) : null;
    }

    /*
     * main methods
     */
    @Override
    public List<Music> findAll() {
        QMusic music = QMusic.music;
        QPlayable playable = QPlayable.playable;

        return queryFactory
                .selectDistinct(music).from(music)
                .leftJoin(music.album).fetchJoin()
                .leftJoin(music.playables, playable).fetchJoin()
                .leftJoin(playable.playType).fetchJoin()
                .leftJoin(playable.difficultyType).fetchJoin()
                .fetch();
    }

    @Override
    public Page<Music> findAll(Pageable pageable, Long album_id) {
        QMusic music = QMusic.music;
        QPlayable playable = QPlayable.playable;

        JPAQuery<Music> query = queryFactory
                .selectDistinct(music).from(music)
                .leftJoin(music.album).fetchJoin()
                .leftJoin(music.playables, playable).fetchJoin()
                .leftJoin(playable.playType).fetchJoin()
                .leftJoin(playable.difficultyType).fetchJoin()
                .where(albumEq(album_id));

        JPAQuery<Long> count_query = queryFactory
                .select(music.count())
                .from(music)
                .where(albumEq(album_id));

        // count query all
        Long music_counts = count_query.fetchOne();
        if(pageable == null) pageable = PageRequest.of(0, 5);
        List<Music> musics = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(musics, pageable, music_counts);
    }

    @Override
    public Optional<Music> findById(Long aLong) {
        return Optional.empty();
    }
}
