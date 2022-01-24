package com.main.koko_main_api.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Playable extends BaseTimeModel  {
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

    // bpms에 한해서 @RestResource를 적용하지 않는다. (URI을 적용하지 않는다.)
    // @RestResource(exported = false)
    @OneToMany(mappedBy = "playable", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bpm> bpms;

    /*
     * /playables/{id}/bpms, POST
     */
    public void saveBpm(Bpm bpm) {
        this.bpms.add(bpm);
    }

    @Builder
    public Playable(Integer level, List<Bpm> bpms, Music music) {
        this.level = level;
        this.bpms = bpms;
        this.music = music;
    }
}
