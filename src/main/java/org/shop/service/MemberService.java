package org.shop.service;

import org.shop.domain.dto.member.JoinDTO;
import org.shop.domain.dto.member.SearchIdDTO;

public interface MemberService {

    public int join(JoinDTO dto) throws Exception;

    public String searchId(String userName, String userPhone, String userEmail);

    public String searchPw(String userId, String userName, String userEmail);

//    public SearchIdDTO checkResetUser(SearchIdDTO dto);

    public int resetPw(String userId, int cno, String password);

    public String checkCno(SearchIdDTO dto);
}
