package org.shop.domain.dto.admin;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {

    private String pno;

    private String pClassification;

    private String pName;

    private long pPrice;

    private Date pRegDate;

    private String pOpNo;

    private String pSize;

    private String pColor;

    private long pStock;
}
