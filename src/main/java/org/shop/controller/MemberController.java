package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
@Log4j
@AllArgsConstructor
public class MemberController {

    //private MemberService memberService;

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
    public void join(){
        //회원가입 처리
    }

    @GetMapping("/login")
    public void login(String error, String logout, Model model){
        //로그인
        log.info("error : " + error);
        log.info("logout : " + logout);

    }

    @GetMapping("/logout")
    public void logout(){
        log.info("logout");
    }

    @GetMapping("/findId")
    public void getFindId(){
        //아이디 찾기 페이지
    }

    @PostMapping("/findId")
    public void findId(){
        //아이디 찾기 처리
    }

    @GetMapping("/findPw")
    public void getFindPw(){
        //비밀번호 찾기 페이지
    }

    @PostMapping("/findPw")
    public void findPw(){
        //비밀번호 찾기 처리리
    }
}
