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

        return memberService.searchId(userName, userPhone, userEmail);
    }

    @GetMapping("/searchPw")
    public void searchPw(){

    }

    @PostMapping("/searchPw")
    @ResponseBody
    public String searchPwProc(@RequestParam("userId") String userId
                                , @RequestParam("userName") String userName
                                , @RequestParam("userEmail") String userEmail){

        log.info("userId : " + userId + ", userName : " + userName);

        return memberService.searchPw(userId, userName, userEmail);
    }


    @PostMapping("/certifyPw")
    @ResponseBody
    public String certifyPw(@RequestParam("userId") String userId
                            , @RequestParam("cno") int cno) throws IOException {

        log.info("certifyPw");

        SearchIdDTO dto = SearchIdDTO.builder()
                .userId(userId)
                .cno(cno)
                .build();

        return memberService.checkCno(dto);
    }

    @GetMapping("/pwReset")
    public String resetPassword(@RequestParam("userId") String userId,
                                @RequestParam("cno") int cno, Model model){
        log.info("id : " + userId + ", cno : " + cno);

        /*
            service에서 userId랑 cno 기반 검증해서 certify에 존재하는지 확인.
            존재한다면 페이지로 리턴.
         */

        SearchIdDTO dto = SearchIdDTO.builder().userId(userId).cno(cno).build();

        /*dto = memberService.checkResetUser(dto);

        log.info("dto : " + dto);

        if(dto != null){
            model.addAttribute("info", dto);

            return "/member/pwReset";
        }else{
            return "/accessError";
        }*/

        String result = memberService.checkCno(dto);

        if(result.equals("success")){
            model.addAttribute("info", dto);
            return "/member/pwReset";
        }else
            return "/accessError";

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
