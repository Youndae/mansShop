package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth {

    private long id;

    private String userId;

    private String auth;
}
