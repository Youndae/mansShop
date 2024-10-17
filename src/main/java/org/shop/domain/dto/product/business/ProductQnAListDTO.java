package org.shop.domain.dto.product.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.ProductQnA;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnAListDTO {

    private long qnaId;

    private String writer;

    private String qnaContent;

    private LocalDate createdAt;

    private boolean status;

    List<ProductQnAReplyListDTO> replyList;

    public ProductQnAListDTO(ProductQnA entity, List<ProductQnAReplyListDTO> replyList) {
        this.qnaId = entity.getId();
        this.writer = entity.getUserId();
        this.qnaContent = entity.getQnaContent();
        this.createdAt = entity.getCreatedAt();
        this.status = entity.isStatus();
        this.replyList = replyList;
    }
}
