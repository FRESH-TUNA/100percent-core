package com.main.koko_main_api.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


//https://www.baeldung.com/rest-api-search-language-spring-data-querydsl

@Getter
@NoArgsConstructor
@Entity
public class Pattern extends BaseTimeModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", nullable = false)
    private Music music;

    // 별의 갯수 (1~15)
    @Column
    @Min(1) @Max(15)
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficulty_type_id", nullable = false)
    private DifficultyType difficultyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_type_id", nullable = false)
    private PlayType playType;


    /*
     * update
     */
    public void update(Pattern new_pattern) {
        this.music = new_pattern.getMusic();
        this.difficultyType = new_pattern.getDifficultyType();
        this.playType = new_pattern.getPlayType();
        this.level = new_pattern.getLevel();
    }

    /*
     * for new Playable and Test purpose
     */
    @Builder
    public Pattern(Long id, Music music,
                   Integer level,
                   DifficultyType difficultyType,
                   PlayType playType) {
        this.id = id;
        this.music = music;
        this.level = level;
        this.difficultyType = difficultyType;
        this.playType = playType;
    }
}
