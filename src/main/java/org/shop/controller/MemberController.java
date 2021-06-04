package org.shop.controller;

import lombok.extern.log4j.Log4j;
import org.shop.mapper.MemberMapper;
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
public class MemberController {

    //private MemberService memberService;

    @Autowired(required = false)
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
    public void join(){
        //회원가입 처리
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


    @PostMapping("/checkUserId")
    @ResponseBody
    public int checkUserId(@RequestParam("UserId") String userId) throws Exception{

        log.info("userId : " + userId);

        log.info("checkUserId : " + memberMapper.idCheck(userId));
        //회원가입 시 아이디 중복 체크

        return memberMapper.idCheck(userId);
    }
}
