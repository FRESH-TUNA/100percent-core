package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Models.Types;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// crud method 숨기기
// https://docs.spring.io/spring-data/rest/docs/current/reference/html/#customizing-sdr.hiding-repository-crud-methods

@RepositoryRestResource(collectionResourceRel = "types", path = "types")
public interface TypesRepository extends JpaRepository<Types, Long> {
}
