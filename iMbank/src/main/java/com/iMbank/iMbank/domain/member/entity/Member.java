package com.iMbank.iMbank.domain.member.entity;

import com.iMbank.iMbank.domain.counsel.entity.Counsel;
import com.iMbank.iMbank.domain.member.entity.enums.MemberRole;
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
public class Member {
    @Id
    @Comment("행번")
    @Column(columnDefinition = "int")
    private int user_id;

    @Comment("이메일")
    @Column(nullable = false)
    private String email;

    @Comment("비밀번호")
    @Column(columnDefinition = "VARCHAR(80)")
    private String password;

    @Comment("이름")
    @Column(columnDefinition = "VARCHAR(40)")
    private String name;

    @Comment("프로필 이미지 URL")
    private String profileImage;

    @Comment("권한")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @Comment("소속 코드명")
    @Column(nullable = false)
    private String dept_nm;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Counsel> counsels = new ArrayList<>();
}
