package org.shop.domain.dto.product.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewListDTO {

    private String reviewer;

    private String reviewContent;

    private LocalDate createdAt;

    private String answerContent;

    private LocalDate answerCreatedAt;
}
