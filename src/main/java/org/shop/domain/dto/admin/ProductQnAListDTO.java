package org.shop.domain.dto.admin;

import lombok.*;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnAListDTO {

    private long pQnANo;

    private String pName;

    private int pQnAAnswer;

    private Date pQnARegDate;

    private int pQnAIndent;

    private String userId;
}
