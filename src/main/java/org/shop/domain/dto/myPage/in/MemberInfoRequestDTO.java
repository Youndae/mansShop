package org.shop.domain.dto.myPage.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.Member;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoRequestDTO {

    private String nickname;

    private String phone;

    private String email;

    public Member toEntity(String userId) {
        String phoneRegEx = "(\\d{3})(\\d{3,4})(\\d{4})";
        return Member.builder()
                .userId(userId)
                .nickname(this.nickname)
                .phone(this.phone.replaceAll(phoneRegEx, "$1-$2-$3"))
                .userEmail(this.email)
                .build();
    }
}
