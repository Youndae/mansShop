package org.shop.domain.dto.admin.member.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AdminMemberPointRequestDTO {

    private String userId;

    private int point;
}
