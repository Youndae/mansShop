package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
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

}
