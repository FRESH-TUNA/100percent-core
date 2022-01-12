package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Models.Composer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "composers", path = "composers")
public interface ComposesRepository extends JpaRepository<Composer, Long> {
}
