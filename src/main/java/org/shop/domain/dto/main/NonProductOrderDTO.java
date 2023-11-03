package org.shop.domain.dto.main;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonProductOrderDTO {

    private String recipient;

    private String orderPhone;
}
