package org.shop.security.domain;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.shop.domain.dto.member.MemberDTO;
import org.shop.domain.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Log4j
public class CustomUser extends User {

    private MemberDTO member;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
    }

    public CustomUser(MemberDTO dto){

        super(dto.getUserId(), dto.getUserPw(), dto.getAuthList().stream()
        .map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));

        log.info("Custom User");
        log.info("info : " + dto.getUserId() + "AuthList" + dto.getAuthList());

        this.member = dto;

    }
}
