package com.main.koko_main_api.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;


@Getter
@NoArgsConstructor
@Entity
public class Bpm extends BaseTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 별의 갯수 (1~15)
    @Column(nullable = false)
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "playable_id")
    private Playable playable;

    @Builder
    public Bpm(Integer value) {
        this.value = value;
    }
}
