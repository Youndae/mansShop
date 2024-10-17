package org.shop.domain.dto.member.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.Member;

@Getter
@ToString
@NoArgsConstructor
public class MemberSearchIdDTO {
    private String userName;

    private String userPhone;

    private String userEmail;

    public Member toEntity() {
        return Member.builder()
                .userName(this.userName)
                .phone(this.userPhone)
                .userEmail(this.userEmail)
                .build();
    }
}
