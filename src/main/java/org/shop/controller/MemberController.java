package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.MemberVO;
import org.shop.mapper.MemberMapper;
import org.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/member/")
@Controller
@Log4j
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    private MemberMapper memberMapper;

    @GetMapping("/accessError")
    public void accessDenied(Authentication auth, Model model){
        log.info("access Denied : " + auth);

        model.addAttribute("msg", "Access Denied");
    }

    @GetMapping("/join")
    public void getJoin(){
        //회원가입 페이지
    }

    @PostMapping("/join")
    public String join(MemberVO memberVO) throws Exception{
        //회원가입 처리

        log.info("memberVO : " + memberVO);

        memberService.join(memberVO);

        log.info("join Success");

        return "member/login";
    }

    @GetMapping("/login")
    public void login(String error, String logout, Model model, HttpServletRequest request){
        //로그인
        log.info("error : " + error);
        log.info("logout : " + logout);

        String referer = request.getHeader("Referer");

        log.info("Referer : " + referer);

        request.getSession().setAttribute("prevPage", referer);
    }

    @GetMapping("/logout")
    public void logout(){
        log.info("logout");
    }

    @PostMapping("/checkUserId")
    @ResponseBody
    public int checkUserId(@RequestParam("UserId") String userId) throws Exception{

        log.info("userId : " + userId);

        log.info("checkUserId : " + memberMapper.idCheck(userId));
        //회원가입 시 아이디 중복 체크

        return memberMapper.idCheck(userId);
    }
}
