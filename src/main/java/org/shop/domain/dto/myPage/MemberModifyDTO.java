package org.shop.domain.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberModifyDTO {

    private String userId;

    private String userName;

    private String userPhone;

    private String userEmail;
}
