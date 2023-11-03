package org.shop.domain.dto.myPage;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MemberOrderListDTO {

    private String orderNo;

    private String userId;

    private Date orderDate;

    private int orderStat;

    private int orderCount;


    private long orderPrice;

    private long odPrice;

    private int reviewStat;

    private String pSize;

    private String pColor;

    private String pName;

    private String firstThumbnail;

    private String pOpNo;

    private String pno;
}
