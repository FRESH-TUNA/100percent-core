package com.main.koko_main_api.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

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

    @OneToMany(mappedBy = "playable")
    private List<Bpm> bpms;
}
