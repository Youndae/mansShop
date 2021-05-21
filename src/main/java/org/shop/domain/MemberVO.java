package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String userId;
    private String userPw;
    private String userName;
    private String email;
    private Date userBirth;
    private String userPhone;
}
