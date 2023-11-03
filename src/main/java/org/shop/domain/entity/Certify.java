package org.shop.domain.entity;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Certify {

    private int cno;

    private String userId;

    private String userPhone;

    private String userEmail;

    private String userName;
}
