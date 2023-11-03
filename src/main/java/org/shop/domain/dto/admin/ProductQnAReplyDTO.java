package org.shop.domain.dto.admin;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQnAReplyDTO {

    private String pno;

    private String pQnAContent;

    private long pQnANo;
}
