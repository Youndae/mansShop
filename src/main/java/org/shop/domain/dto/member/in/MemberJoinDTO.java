package org.shop.domain.dto.member.in;

import lombok.*;
import org.shop.domain.entity.Member;


@Getter
@ToString
@NoArgsConstructor
public class MemberJoinDTO {

    private String userId;

    private String userPw;

    private String username;

    private String nickname;

    private String phone;

    private String birth;

    private String email;
}
