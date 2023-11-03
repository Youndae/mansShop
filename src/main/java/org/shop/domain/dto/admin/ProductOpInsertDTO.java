package org.shop.domain.dto.admin;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOpInsertDTO {

    private String pno;

    private String pSize;

    private String pColor;

    private Long pStock;

    private String pClassification;
}
