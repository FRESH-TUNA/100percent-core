package com.main.koko_main_api.repositories;

import com.main.koko_main_api.domains.Composer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "composers", path = "composers")
public interface ComposerRepository extends JpaRepository<Composer, Long> {

    List<Composer> findByNameContaining(String name);
}
