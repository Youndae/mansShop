package org.shop.domain.dto.myPage;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ProductOrderInfoDTO {

    private String pno;

    private String orderNo;

    private String pName;
}
