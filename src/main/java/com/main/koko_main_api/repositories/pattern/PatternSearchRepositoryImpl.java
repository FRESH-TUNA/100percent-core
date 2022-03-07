//package com.main.koko_main_api.repositories.pattern;
//
//import com.main.koko_main_api.domains.Pattern;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//
//import java.util.List;
//import java.util.Optional;
//
///*
// * 참고자료
// * https://velog.io/@max9106/JPA-QueryDSL
// * https://joanne.tistory.com/270
// * https://jojoldu.tistory.com/372
// * https://jessyt.tistory.com/55
// */
//
////@Repository
//public class PatternSearchRepositoryImpl
//        extends QuerydslRepositorySupport
//        implements PatternSearchRepository<Pattern, Long> {
//
//    private final JPAQueryFactory queryFactory;
//
//    public PatternSearchRepositoryImpl(JPAQueryFactory queryFactory) {
//        super(Pattern.class);
//        this.queryFactory = queryFactory;
//    }
//
//    @Override
//    public Optional<Pattern> findById(Long id) {
//        QPlayable playable = QPlayable.playable;
//        List<Pattern> patterns = queryFactory
//                .selectDistinct(playable)
//                .from(playable).where(playable.id.eq(id))
//                .leftJoin(playable.music).fetchJoin()
//                .leftJoin(playable.playType).fetchJoin()
//                .leftJoin(playable.difficultyType).fetchJoin()
//                .fetch();
//
//        if(patterns.size() == 0) return Optional.empty();
//        else return Optional.of(patterns.get(0));
//    }
//}
