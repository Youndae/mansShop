package org.shop.domain.dto.admin;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MemberInfoDTO {

    private String userId;

    private String userName;

    private String userEmail;

    private Date joinRegDate;

    private Date userBirth;

    private String userPhone;
}
