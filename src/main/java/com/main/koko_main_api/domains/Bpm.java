package com.main.koko_main_api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import lombok.Builder;


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

    /*
     * 양방향 연관관계는 양쪽 다 데이터를 넣어주는것이 좋다.
     */
    @Builder
    public Bpm(Integer value, Playable playable) {
        this.playable = playable;
        this.playable.getBpms().add(this);
        this.value = value;
    }
}