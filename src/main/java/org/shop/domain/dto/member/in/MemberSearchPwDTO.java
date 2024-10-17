package org.shop.domain.dto.member.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.Member;

@Getter
@ToString
@NoArgsConstructor
public class MemberSearchPwDTO {
    private String userId;

    private String userName;

    private String userEmail;

    public Member toEntity() {
        return Member.builder()
                .userId(this.userId)
                .userName(this.userName)
                .userEmail(this.userEmail)
                .build();
    }
}
