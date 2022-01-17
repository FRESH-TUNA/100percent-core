package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Dtos.BpmsSaveDto;
import com.main.koko_main_api.Models.Bpm;
import com.main.koko_main_api.Models.Playable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "playables", path = "playables")
public interface PlayablesRepository extends JpaRepository<Playable, Long> {
}
