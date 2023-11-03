package org.shop.domain.dto.member;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchIdDTO {

    private String userId;

    private int cno;
}
