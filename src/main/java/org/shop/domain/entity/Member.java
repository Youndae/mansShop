package org.shop.domain.entity;

import lombok.*;
import org.shop.domain.dto.member.in.MemberJoinDTO;
import org.shop.domain.dto.member.in.MemberResetPwDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    private String userId;

    private String userPw;

    private String userName;

    private String nickname;

    private String userEmail;

    private String provider;

    private LocalDate createdAt;

    private int memberPoint;

    private String phone;

    private LocalDate birth;


    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public Member(MemberJoinDTO joinDTO) {
        String phoneRegEx = "(\\d{3})(\\d{3,4})(\\d{4})";
        int[] birthArray = Arrays.stream(joinDTO.getBirth().split("/")).mapToInt(Integer::parseInt).toArray();
        LocalDate birth = LocalDate.of(birthArray[0], birthArray[1], birthArray[2]);

        this.userId = joinDTO.getUserId();
        this.userPw = encodePassword(joinDTO.getUserPw());
        this.userName = joinDTO.getUsername();
        this.nickname = joinDTO.getNickname();
        this.phone = joinDTO.getPhone().replaceAll(phoneRegEx, "$1-$2-$3");
        this.birth = birth;
        this.userEmail = joinDTO.getEmail();
        this.provider = "local";
    }

    public Member(MemberResetPwDTO resetPwDTO) {
        this.userId = resetPwDTO.getUserId();
        this.userPw = encodePassword(resetPwDTO.getPassword());
    }

    private String encodePassword(String userPw) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(userPw);
    }
}
