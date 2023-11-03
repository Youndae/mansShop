package org.shop.domain.dto.admin;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MemberListDTO {

    private String userId;

    private String userName;

    private Date joinRegDate;
}
