package com.iMbank.iMbank.domain.wicket.entity;

import com.iMbank.iMbank.domain.counsel.entity.Counsel;
import com.iMbank.iMbank.domain.department.entity.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Wicket {
    @Id
    @Comment("창구 아이디")
    @Column(columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wd_id;

    @Comment("소속 코드")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @Comment("창구 구분 코드")
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String wd_dvcd;

    @Comment("창구 번호")
    @Column(columnDefinition = "int", nullable = false)
    private int wd_num;

    @Comment("층 수")
    @Column(columnDefinition = "int", nullable = false)
    private int wd_floor;

    @Comment("행 번호")
    @Column(columnDefinition = "int")
    private int rowNum;

    @Comment("열 번호")
    @Column(columnDefinition = "int")
    private int colNum;

    @Comment("담당 직원 아이디")
    @Column(columnDefinition = "int", nullable = false)
    private int user_id;


    @OneToMany(mappedBy = "wicket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Counsel> counsels = new ArrayList<>();
}
