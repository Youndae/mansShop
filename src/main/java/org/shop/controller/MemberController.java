package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.dto.member.business.SearchIdDTO;
import org.shop.domain.dto.member.in.MemberResetPwDTO;
import org.shop.domain.enumeration.Result;
import org.shop.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestMapping("/member/")
@Controller
@Log4j
@RequiredArgsConstructor
@PreAuthorize("isAnonymous()")
public class MemberController {

    private final MemberService memberService;

    //회원 가입 페이지
    @GetMapping("/join")
    public void getJoin(){

    }

    //로그인 페이지
    @GetMapping("/login")
    public String login(HttpServletRequest request, Principal principal){
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referer);

        if(referer != null && referer.equals("http://localhost:8080/member/login") && principal != null){
            return "redirect:/";
        }else if(principal != null && referer != null){
            return "redirect:" + referer;
        }else if(referer == null){
            return "redirect:/";
        }

        return "member/login";
    }

    //아이디찾기
    @GetMapping("/search-id")
    public String searchId(){

        return "member/searchId";
    }

    @GetMapping("/search-pw")
    public String searchPw(){

        return "member/searchPw";
    }

    @GetMapping("/reset-pw")
    public String resetPassword(@RequestParam("userId") String userId,
                                @RequestParam("cno") int cno, Model model){
        /*
            service에서 userId랑 cno 기반 검증해서 certify에 존재하는지 확인.
            존재한다면 페이지로 리턴.
         */
        MemberResetPwDTO resetPwDTO = new MemberResetPwDTO(userId, cno);
        String result = memberService.checkCno(resetPwDTO);
        SearchIdDTO dto = SearchIdDTO.builder()
                                    .userId(userId)
                                    .cno(cno)
                                    .build();

        if(result.equals(Result.SUCCESS.getResultKey())){
            model.addAttribute("info", dto);
            return "/member/pwReset";
        }else
            return "/accessError";
    }

    @PostMapping("/reset-pw")
    public String resetPw(@RequestParam("userId") String userId
                        , @RequestParam("cno") int cno
                        , @RequestParam("password") String password){
        log.info("MemberController.resetPw");
        MemberResetPwDTO resetPwDTO = new MemberResetPwDTO(userId, cno, password);
        String result = memberService.resetPw(resetPwDTO);

        if(result.equals(Result.SUCCESS.getResultKey()))
            return "redirect:/member/login";
        else
            return "redirect:/accessError";
    }
}
