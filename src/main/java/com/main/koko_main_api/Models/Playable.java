package com.main.koko_main_api.Models;

import com.main.koko_main_api.Dtos.BpmsSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Playable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "music_id")
    private Music music;

    // 별의 갯수 (1~15)
    @Column(nullable = false)
    @Min(1) @Max(15)
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficulty_type_id")
    private DifficultyType difficultyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_type_id")
    private PlayType playType;

    @OneToMany(mappedBy = "playable", cascade = CascadeType.ALL)
    private Set<Bpm> bpms = new HashSet<>();

    /*
     * /playables/{id}/bpms, POST
     */
    public void saveBpm(Bpm bpm) {
        this.bpms.add(bpm);
    }

    @Builder
    public Playable(Integer level, Set<Bpm> bpms) {
        this.level = level;
        this.bpms = bpms;
    }
}
