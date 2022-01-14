package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Models.Bpm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bpms", path = "bpms")
public interface BpmsRepository extends JpaRepository<Bpm, Long> {
}