package com.main.koko_main_api.Models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
@Entity
public class Playables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 별의 갯수 (1~15)
    @Column(nullable = false)
    @Min(1) @Max(15)
    private Integer level;

    // easy, normal, hard, sc = 1, 2, 3, 4
    @Column(nullable = false)
    @Min(1) @Max(4)
    private Integer difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "types_id")
    private Types type;

    @Builder
    public Playables(String title) {
        this.title = title;
    }
}
