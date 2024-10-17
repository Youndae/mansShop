package org.shop.domain.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductQnAReply {

    private long id;

    private String userId;

    private long qnaId;

    private String replyContent;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
