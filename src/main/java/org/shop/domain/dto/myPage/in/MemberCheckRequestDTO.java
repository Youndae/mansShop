package org.shop.domain.dto.myPage.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberCheckRequestDTO {

    private String userId;

    private String userPw;
}
