package com.iMbank.iMbank.domain.member.dto;

import com.iMbank.iMbank.domain.member.entity.Member;
import com.iMbank.iMbank.domain.member.entity.enums.MemberRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberSignupRequest {

    @NotNull(message = "User ID는 null일 수 없습니다.")
    private Integer user_id;


    @NotBlank(message = "이메일은 필수 입력값입니다")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    @Size(min = 2, max = 12, message = "이름은 2자 이상 12자 이하로 입력해주세요.")
    private String name;

    @Size(min = 2, max = 18, message = "닉네임은 2자 이상 18자 이하로 입력해주세요.")
    private String nickname;

    private String profileImage;

    public Member toEntity() {
        return Member.builder()
                .user_id(user_id)
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .profileImage(profileImage)
                .role(MemberRole.BRANCH)
                .build();
    }
}
