package org.shop.domain.dto.myPage.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.Member;
import org.shop.domain.enumeration.MailSuffix;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyPageMemberInfoDTO {
    private String nickname;

    private String phone;

    private String mailPrefix;

    private String mailSuffix;

    private String mailType;

    public MyPageMemberInfoDTO(Member member, String[] splitMail) {
        this.nickname = member.getNickname();
        this.phone = member.getPhone().replaceAll("-", "");
        this.mailPrefix = splitMail[0];
        this.mailSuffix = splitMail[1];
        this.mailType = MailSuffix.findSuffixType(splitMail[1]);
    }
}
