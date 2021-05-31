package org.shop.security;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.shop.domain.MemberVO;
import org.shop.mapper.MemberMapper;
import org.shop.mapper.MyPageMapper;
import org.shop.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j
public class CustomUserDetailService implements UserDetailsService {

    @Setter(onMethod_ = @Autowired)
    private MyPageMapper myPageMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("Load User By UserName : " + username);

        MemberVO vo = myPageMapper.getModifyInfo(username);

        log.warn("member mapper : " + vo);

        return vo == null ? null : new CustomUser(vo);
    }
}
