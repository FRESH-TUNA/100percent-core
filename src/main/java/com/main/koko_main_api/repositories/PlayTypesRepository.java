package com.main.koko_main_api.repositories;

import com.main.koko_main_api.domains.PlayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// crud method 숨기기
// https://docs.spring.io/spring-data/rest/docs/current/reference/html/#customizing-sdr.hiding-repository-crud-methods

@RepositoryRestResource(collectionResourceRel = "play_types", path = "play_types")
public interface PlayTypesRepository extends JpaRepository<PlayType, Long> {
}
