package com.iMbank.iMbank.domain.counsel.entity;


import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.member.entity.Member;
import com.iMbank.iMbank.domain.wicket.entity.Wicket;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Counsel {
    @Id
    @Comment("상담 아이디")
    @Column(columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int counsel_id;

    @Comment("소속 코드")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @Comment("업무 구분 코드")
    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    private String user_dvcd;

    @Comment("번호표")
    @Column(columnDefinition = "int", nullable = false)
    private int ticket_count;

    @Comment("일자")
    @Column(columnDefinition = "VARCHAR(8)", nullable = false)
    private String crdt;

    @Comment("키오스크 아이디")
    @Column(columnDefinition = "int", nullable = false)
    private int kiosk_id;

    @Comment("상담 코드")
    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    private String csnl_cd;

    @CreatedDate
    @Comment("발급시간")
    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ticket_stime;

    @Comment("상담 대면 시작 시각")
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime csnl_start_dt;

    @Comment("상담 대면 종료 시각")
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime csnl_end_dt;


    @Comment("대기 시간")
    @Column(columnDefinition = "int")
    private int wait_time;

    @Comment("상담 시간")
    @Column(columnDefinition = "int")
    private int csnl_time;

    @Comment("행번")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Comment("창구 아이디")
    @Column(columnDefinition = "int")
    private int wd_id;

    public Counsel(Department deptId, String userDvcd, int count, String todayDate, int kioskId, String csnlCd, Member member, int wicket, int waitTime, int csnlTime, Timestamp csnlStart, Timestamp csnlEnd) {
        this.department = deptId;
        this.user_dvcd = userDvcd;
        this.ticket_count = count;
        this.crdt = todayDate;
        this.kiosk_id = kioskId;
        this.csnl_cd = csnlCd;
        this.member = member;
        this.wd_id = wicket;
        this.wait_time = waitTime;
        this.csnl_time = csnlTime;
    }
}
