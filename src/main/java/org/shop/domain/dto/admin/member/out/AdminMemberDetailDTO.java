package org.shop.domain.dto.admin.member.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.Member;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class AdminMemberDetailDTO {

    private String userId;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private LocalDate birth;

    private int point;

    private LocalDate createdAt;

    public AdminMemberDetailDTO(Member entity) {
        this.userId = entity.getUserId();
        this.username = entity.getUserName();
        this.nickname = entity.getNickname() == null ? "미설정" : entity.getNickname();
        this.phone = entity.getPhone();
        this.email = entity.getUserEmail();
        this.birth = entity.getBirth();
        this.point = entity.getMemberPoint();
        this.createdAt = entity.getCreatedAt();
    }
}
