package com.main.koko_main_api.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//https://www.baeldung.com/rest-api-search-language-spring-data-querydsl

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Playable extends BaseTimeModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    // 별의 갯수 (1~15)
    @Column
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
    @OneToMany(mappedBy = "playable")
    private List<Bpm> bpms = new ArrayList<>();

    /*
     * /playables/{id}/bpms, POST
     */
    // 연관관계의 주인이 아니기 때문에 무시될것 같다.
//    public void addBpm(Bpm bpm) {
//        this.bpms.add(bpm);
//    }

    /*
     * for new Playable
     * 실제 쿼리가 들어가지는 않지만 객체의 양방향 연결을 위해 사용한다.
     * 또한 객체 address를 바꾸지 않아서 필요없는 update쿼리를 막는다.
     */
    public void add_bpms_for_save_request(List<Bpm> bpms) {
        Iterator<Bpm> it = bpms.iterator();
        while(it.hasNext()) this.bpms.add(it.next());
    }

    /*
     * for new Playable and Test purpose
     */
    @Builder
    public Playable(Long id, Music music,
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
