package org.shop.domain.dto.member.business;

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
