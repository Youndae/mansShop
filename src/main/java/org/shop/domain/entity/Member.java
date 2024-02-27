package org.shop.domain.entity;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class Member {

    private String userId;

    private String userPw;

    private String userName;

    private String userEmail;

    private Date userBirth;

    private String userPhone;

    private Date joinRegDate;

    public Member(String userId, String userPw, String userName, String userEmail, Date userBirth, String userPhone, Date joinRegDate) {
        this.userId = userId;
        this.userPw = userPw == null ? null : encodePassword(userPw);
        this.userName = userName == "" ? null : userName;
        this.userEmail = userEmail == "" ? null : userEmail;
        this.userBirth = userBirth;
        this.userPhone = userPhone;
        this.joinRegDate = joinRegDate;
    }

    private String encodePassword(String pw) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(pw);
    }
}
