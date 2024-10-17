package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberQnA {

    private long id;

    private String userId;

    private int qnaClassificationId;

    private String memberQnATitle;

    private String memberQnAContent;

    private boolean status;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
