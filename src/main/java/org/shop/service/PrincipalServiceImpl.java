package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.exception.customException.CustomAccessDeniedException;
import org.shop.exception.enumeration.ErrorCode;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalServiceImpl implements PrincipalService{

    @Override
    public String getUserIdToPrincipal(Principal principal) {

        if(principal == null)
            throw new CustomAccessDeniedException(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

        return principal.getName();
    }
}
