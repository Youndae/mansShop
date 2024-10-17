package org.shop.domain.dto.member.business;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthDTO {

    private String userId;

    private String auth;
}
