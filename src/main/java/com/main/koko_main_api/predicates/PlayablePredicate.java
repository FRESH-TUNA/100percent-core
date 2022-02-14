package com.main.koko_main_api.predicates;

import com.main.koko_main_api.domains.Playable;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//public class PlayablePredicate {
//    public static BooleanBuilder
//}


/*
 * criteria style
 */
//public class PlayablePredicate {
//    public static Specification<Playable> findAll(Map<String, Object> filter) {
//        /*
//         * root : Entity
//         * query : ?
//         * criteria: jpql을 좀더 이쁘게 작성할수 있도록 도와주는 클래스
//         */
//        return (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            filter.forEach((key, value) -> {
//                predicates.add(criteriaBuilder.equal(root.get(key), value));
//            });
//
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        };
//    }
//}