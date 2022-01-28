package com.main.koko_main_api.repositories;

import com.main.koko_main_api.domains.Bpm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bpms", path = "bpms")
public interface BpmsRepository extends JpaRepository<Bpm, Long> {

}