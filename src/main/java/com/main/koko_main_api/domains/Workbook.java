package com.main.koko_main_api.domains;

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

    @OneToMany(mappedBy = "workbook")
    private List<WorkbookPattern> patterns = new ArrayList<>();
}
