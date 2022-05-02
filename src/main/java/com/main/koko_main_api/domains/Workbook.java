package com.main.koko_main_api.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Workbook extends BaseTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "play_type_id")
    private PlayType playType;

    @OneToMany(mappedBy = "workbook", cascade = CascadeType.ALL)
    private List<WorkbookPattern> patterns = new ArrayList<>();

    /*
     * methods
     */
    public void add_patterns(List<WorkbookPattern> patterns) {
        this.patterns = patterns;
    }

    @Builder
    public Workbook(Long id, String title, String description,
                    List<WorkbookPattern> patterns,
                    PlayType playType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.patterns = patterns;
        this.playType = playType;
    }
}
