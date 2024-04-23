package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.member.JoinDTO;
import org.shop.domain.dto.member.SearchIdDTO;
import org.shop.mapper.MemberMapper;
import org.shop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@RequestMapping("/member/")
@Controller
@Log4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberMapper memberMapper;

    //회원 가입 페이지
    @GetMapping("/join")
    public void getJoin(){

    }

    //회원가입 처리
    @PostMapping("/join")
    public String join(JoinDTO dto) {
        memberService.join(dto);

        return "member/login";
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

    //회원가입 중 아이디 중복 체크
    @PostMapping("/check-id")
    @ResponseBody
    public String checkUserId(@RequestParam("uid") String userId) {

        return String.valueOf(memberMapper.idCheck(userId));
    }

    //아이디찾기
    @GetMapping("/search-id")
    public void searchId(){

    }

    @PostMapping("/search-id")
    @ResponseBody
    public String searchIdProc(@RequestParam("userName") String userName
                                , @RequestParam(value = "userPhone", required = false) String userPhone
                                , @RequestParam(value = "userEmail", required = false) String userEmail){

        return memberService.searchId(userName, userPhone, userEmail);
    }

    @GetMapping("/search-pw")
    public void searchPw(){

    }

    @PostMapping("/search-pw")
    @ResponseBody
    public String searchPwProc(@RequestParam("userId") String userId
                                , @RequestParam("userName") String userName
                                , @RequestParam("userEmail") String userEmail){

        return memberService.searchPw(userId, userName, userEmail);
    }


    @PostMapping("/certify-pw")
    @ResponseBody
    public String certifyPw(@RequestParam("userId") String userId
                            , @RequestParam("cno") int cno) {

        return memberService.checkCno(userId, cno);
    }

    @GetMapping("/reset-pw")
    public String resetPassword(@RequestParam("userId") String userId,
                                @RequestParam("cno") int cno, Model model){
        /*
            service에서 userId랑 cno 기반 검증해서 certify에 존재하는지 확인.
            존재한다면 페이지로 리턴.
         */

        String result = memberService.checkCno(userId, cno);
        SearchIdDTO dto = SearchIdDTO.builder()
                                    .userId(userId)
                                    .cno(cno)
                                    .build();

        if(result.equals(ResultProperties.SUCCESS)){
            model.addAttribute("info", dto);
            return "/member/pwReset";
        }else
            return "/accessError";

    }

    @PostMapping("/reset-pw")
    public String resetPw(@RequestParam("userId") String userId
                            , @RequestParam("cno") int cno
                            , @RequestParam("password") String password){
        String result = memberService.resetPw(userId, cno, password);

        if(result.equals(ResultProperties.SUCCESS))
            return "redirect:/member/login";
        else
            return "redirect:/accessError";
    }
}
