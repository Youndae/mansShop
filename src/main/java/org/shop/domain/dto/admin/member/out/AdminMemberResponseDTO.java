package org.shop.domain.dto.admin.member.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class AdminMemberResponseDTO {
    private String userId;

    private String name;

    private String nickname;

    private LocalDate createdAt;
}
