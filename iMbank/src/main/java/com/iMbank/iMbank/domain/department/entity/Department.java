package com.iMbank.iMbank.domain.department.entity;

import com.iMbank.iMbank.domain.counsel.entity.Counsel;
import com.iMbank.iMbank.domain.kiosk.entity.Kiosk;
import com.iMbank.iMbank.domain.wicket.entity.Wicket;
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
public class Department {
    @Id
    @Comment("소속 코드")
    @Column(columnDefinition = "VARCHAR(10)")
    private String dept_id;

    @Comment("조직 축약명")
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String dept_nm;

    @Comment("영업 시작 시간")
    @Column(columnDefinition = "VARCHAR(4)", nullable = false)
    private String stime;

    @Comment("영업 종료 시간")
    @Column(columnDefinition = "VARCHAR(4)", nullable = false)
    private String etime;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wicket> windows = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kiosk> kiosks = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Counsel> counsels = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Work> works = new ArrayList<>();
}