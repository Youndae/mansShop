package org.shop.domain.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyQnAInsertDTO {

    private String qTitle;

    private String qContent;
}
