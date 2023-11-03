package org.shop.domain.dto.myPage;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LikeListDTO {

    private String pno;

    private String firstThumbnail;

    private String pName;

    private long pPrice;
}
