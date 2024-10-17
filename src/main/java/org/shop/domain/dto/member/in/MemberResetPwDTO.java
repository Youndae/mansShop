package org.shop.domain.dto.member.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberResetPwDTO {

    private String userId;

    private int cno;

    private String password;

    public MemberResetPwDTO(String userId, int cno) {
        this.userId = userId;
        this.cno = cno;
    }
}
