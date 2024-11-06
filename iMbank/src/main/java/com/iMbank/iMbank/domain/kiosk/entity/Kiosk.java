package com.iMbank.iMbank.domain.kiosk.entity;

import com.iMbank.iMbank.domain.counsel.entity.Ticket;
import com.iMbank.iMbank.domain.department.entity.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Kiosk {
    @Id
    @Comment("키오스크 아이디")
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kiosk_id;

    @Comment("소속 코드")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @Comment("행 번호")
    @Column(columnDefinition = "int")
    private int rowNum;

    @Comment("열 번호")
    @Column(columnDefinition = "int")
    private int colNum;

    @Comment("주소")
    @Column(columnDefinition = "VARCHAR(100)")
    private String address;


    @OneToMany(mappedBy = "kiosk", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>(); // 티켓 리스트
}
