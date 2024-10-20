package org.shop.domain.dto.member.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private String userId;

    private String userPw;

    private List<MemberAuthDTO> authList;
}
