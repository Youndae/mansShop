package org.shop.domain.entity;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductThumbnail {

    private String thumbNo;

    private String pno;

    private String pThumbnail;
}
