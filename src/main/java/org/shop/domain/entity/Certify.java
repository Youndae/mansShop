package org.shop.domain.entity;

import lombok.*;

/**
 * Redis로 전환
 */
@Deprecated
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
