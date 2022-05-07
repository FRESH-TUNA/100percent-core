package com.main.koko_main_api.repositories;

import com.main.koko_main_api.domains.DifficultyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "difficulty_types", path = "difficulty_types")
public interface DifficultyTypeRepository extends JpaRepository<DifficultyType, Long> {
}
