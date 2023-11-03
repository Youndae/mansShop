package org.shop.domain.dto.admin;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInsertDTO {

    private String pClassification;

    private String pName;

    private long pPrice;

    private String pSize;

    private String pColor;

    private Long pStock;


}
