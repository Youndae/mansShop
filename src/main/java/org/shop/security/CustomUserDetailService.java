package org.shop.security;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.shop.domain.dto.member.business.MemberDTO;
import org.shop.mapper.MemberMapper;
import org.shop.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j
public class CustomUserDetailService implements UserDetailsService {

    @Setter(onMethod_ = @Autowired)
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.warn("Load User By UserName : " + username);

        MemberDTO dto = memberMapper.getUserInfo(username);


        log.warn("member mapper : " + dto);

        return dto == null ? null : new CustomUser(dto);
    }
}
