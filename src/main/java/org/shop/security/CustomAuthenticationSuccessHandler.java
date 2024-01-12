package org.shop.security;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        log.warn("AuthenticationSuccess");

        List<String> roleNames = new ArrayList<>();

        authentication.getAuthorities().forEach(authority->{
            roleNames.add(authority.getAuthority());
        });

        log.warn("role names : " + roleNames);

        if(roleNames.contains("ROLE_ADMIN")){
            response.sendRedirect("/admin/productList");
            return;
        }

        if(roleNames.contains("ROLE_MANAGER")){
            response.sendRedirect("/managerPage/orderList");
            return;
        }

        HttpSession session = request.getSession();

        log.info("auth session : " + session);

        if(session != null){
            String redirectUrl = (String) session.getAttribute("prevPage");
            log.info("RedirectUrl : " + redirectUrl);
            if(redirectUrl != null){
                session.removeAttribute("prevPage");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            }else{
                super.onAuthenticationSuccess(request, response, authentication);
            }

        }else{
            super.onAuthenticationSuccess(request, response, authentication);
        }


        /*RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        log.warn("redirectStrategy : " + redirectStrategy);
        RequestCache requestCache = new HttpSessionRequestCache();
        log.warn("requestCache : " + requestCache);
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        log.warn("savedRequest : " + savedRequest);
        String targetUrl = savedRequest.getRedirectUrl();

        log.warn("targetUrl : " + targetUrl);

        redirectStrategy.sendRedirect(request, response, targetUrl);*/

       // super.onAuthenticationSuccess(request, response, authentication);
    }

}
