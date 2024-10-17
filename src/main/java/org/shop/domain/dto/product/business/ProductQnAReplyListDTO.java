package org.shop.domain.dto.product.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.ProductQnAReply;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnAReplyListDTO {

    private String writer;

    private String content;

    private LocalDate createdAt;

    public ProductQnAReplyListDTO(ProductQnAReply entity) {
        this.writer = entity.getUserId();
        this.content = entity.getReplyContent();
        this.createdAt = entity.getCreatedAt();
    }
}
