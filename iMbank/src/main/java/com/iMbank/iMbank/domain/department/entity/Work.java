package com.iMbank.iMbank.domain.department.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Work {
    @Id
    @Comment("업무 아이디")
    @Column(columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int work_id;

    @Comment("소속 코드")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;


    @Comment("조직 축약명")
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String dept_nm;

    @Comment("업무 구분 코드")
    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    private String work_dvcd;

    @Comment("업무 구분 코드명")
    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String work_dvcd_nm;

    @Comment("순서")
    @Column(columnDefinition = "int")
    @ColumnDefault("0")
    private int loc;

    @Comment("색")
    @ColumnDefault("0")
    @Column(columnDefinition = "int")
    private int color;

    @Comment("왼쪽 위 좌표")
    @Column(columnDefinition = "varchar(20)")
    private String left_high;

    @Comment("오른쪽 아래 좌표")
    @Column(columnDefinition = "varchar(20)")
    private String right_low;

    public Work(Department dept, String dept_nm, String work_dvcd, String work_dvcd_nm, int color, String left_high, String right_low) {
        this.department = dept;
        this.dept_nm = dept_nm;
        this.work_dvcd = work_dvcd;
        this.work_dvcd_nm = work_dvcd_nm;
        this.color = color;
        this.left_high = left_high;
        this.right_low = right_low;
    }
}