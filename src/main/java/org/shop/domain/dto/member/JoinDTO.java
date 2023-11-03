package org.shop.domain.dto.member;

import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {

    private String userId;

    private String userPw;

    private String userName;

    private String userEmail;

    private Date userBirth;

    private String userPhone;

    private Date joinRegDate;

}
