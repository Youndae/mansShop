package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeProduct {

    private String likeNo;

    private String userId;

    private String pno;
}
