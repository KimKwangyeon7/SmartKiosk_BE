package com.iMbank.iMbank.domain.counsel.entity;


import com.iMbank.iMbank.domain.kiosk.entity.Kiosk;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int")
    private int id; // Ticket ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kiosk_id", nullable = false) // 외래 키 설정
    private Kiosk kiosk;

    @Comment("업무 구분 코드")
    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    private String user_dvcd;

    @Column(nullable = false)
    private String ticket_date; // 마지막 초기화 날짜 (YYYYMMDD 형식)

    @Column(nullable = false)
    private int count; // 특정 업무의 티켓 수

    public Ticket(Kiosk kiosk, String user_dvcd, String date, int count) {
        this.kiosk = kiosk;
        this.user_dvcd = user_dvcd;
        this.ticket_date = date;
        this.count = count;
    }
}
