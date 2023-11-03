package org.shop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.shop.domain.ResultProperties;
import org.shop.domain.dto.member.JoinDTO;
import org.shop.domain.dto.member.SearchIdDTO;
import org.shop.domain.entity.Member;
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
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    private MemberMapper memberMapper;

    //회원 가입 페이지
    @GetMapping("/join")
    public void getJoin(){

    }

    //회원가입 처리
    @PostMapping("/join")
    public String join(JoinDTO dto) {
        log.info("memberVO : " + dto);

        int result;

        try{
            result = memberService.join(dto);
        }catch (Exception e){
            result = ResultProperties.ERROR;
        }

        if(result == ResultProperties.ERROR){
            return "/accessError";
        }

        log.info("join Success");

        return "member/login";
    }

    //로그인 페이지
    @GetMapping("/login")
    public String login(String error, HttpServletRequest request, Principal principal){
        log.info("error : " + error);

        String referer = request.getHeader("Referer");

        log.info("Referer : " + referer);

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
    @PostMapping("/checkUserId")
    @ResponseBody
    public String checkUserId(@RequestParam("UserId") String userId) throws Exception{

        log.info("userId : " + userId);

        return String.valueOf(memberMapper.idCheck(userId));
    }

    //아이디찾기
    @GetMapping("/searchId")
    public void searchId(){

    }

    @PostMapping("/searchId")
    @ResponseBody
    public String searchIdProc(@RequestParam("userName") String userName
                                , @RequestParam(value = "userPhone", required = false) String userPhone
                                , @RequestParam(value = "userEmail", required = false) String userEmail){

        log.info("userName : " + userName + ", userPhone : " + userPhone + ", userEmail : " + userEmail);

        if(userPhone == "")
            userPhone = null;

        if(userEmail == "")
            userEmail = null;

        if(userName == null)
            return null;
        else if(userPhone == null && userEmail == null)
            return null;


       Member member = Member.builder()
               .userName(userName)
               .userPhone(userPhone)
               .userEmail(userEmail)
               .build();

       String result = memberMapper.searchId(member);

       log.info("result : " + result);

       if(result == null) {
           log.info("equals empty");
           return null;
       }
        return result;
    }

    @GetMapping("/searchPw")
    public void searchPw(){

    }

    @PostMapping("/searchPw")
    @ResponseBody
    public String searchPwProc(@RequestParam("userId") String userId
                                , @RequestParam("userName") String userName
                                , @RequestParam("userEmail") String userEmail
                                , @RequestParam("searchType") String searchType){

        log.info("userId : " + userId + ", userName : " + userName);

        return memberService.searchPw(userId, userName, userEmail, searchType);

    }


    @PostMapping("/certifyPw")
    @ResponseBody
    public String certifyPw(@RequestParam("userId") String userId
                            , @RequestParam("userName") String userName
                            , @RequestParam(value = "userPhone", required = false) String userPhone
                            , @RequestParam(value = "userEmail", required = false) String userEmail
                            , @RequestParam("cno") int cno) throws IOException {

        log.info("certifyPw");
        log.info("userId : " + userId + ", userName : " + userName + ", userPhone : " + userPhone
         + ", userEmail : " + userEmail + ", cno : " + cno);

        return "success";
    }


    @PostMapping("/pwReset")
    public String resetPassword(@RequestParam("userId") String userId,
                                @RequestParam("cno") int cno, Model model){
        log.info("id : " + userId + ", cno : " + cno);

        /*
            service에서 userId랑 cno 기반 검증해서 certify에 존재하는지 확인.
            존재한다면 페이지로 리턴.
         */

        SearchIdDTO dto = SearchIdDTO.builder().userId(userId).cno(cno).build();

        dto = memberService.checkResetUser(dto);

        log.info("dto : " + dto);

        model.addAttribute("info", dto);

        return "/member/pwReset";
    }

    @PostMapping("/resetPw")
    public String resetPw(@RequestParam("userId") String userId
                            , @RequestParam("cno") int cno
                            , @RequestParam("password") String password){

        log.info("userId : " + userId + ", cno : " + cno + ", password : " + password);

        int result = memberService.resetPw(userId, cno, password);

        if(result == 1)
            return "redirect:/member/login";
        else
            return "redirect:/accessError";

    }
}