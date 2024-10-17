package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.member.in.MemberJoinDTO;
import org.shop.domain.dto.member.in.MemberResetPwDTO;
import org.shop.domain.dto.member.in.MemberSearchIdDTO;
import org.shop.domain.dto.member.in.MemberSearchPwDTO;
import org.shop.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@PreAuthorize("isAnonymous()")
@Slf4j
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/check-id")
    public ResponseEntity<String> checkJoinUserId(@RequestParam("userId") String userId) {

        String result = memberService.checkJoinUserId(userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<String> checkJoinNickname(@RequestParam("nickname") String nickname) {

        String result = memberService.checkJoinNickname(nickname);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinMember(@RequestBody MemberJoinDTO memberJoinDTO) {

        String result = memberService.join(memberJoinDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/search-id")
    public ResponseEntity<String> searchIdProc(@RequestBody MemberSearchIdDTO searchIdDTO){

        String result =  memberService.searchId(searchIdDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/search-pw")
    public ResponseEntity<String> searchPwProc(@RequestBody MemberSearchPwDTO searchPwDTO){
        String result = memberService.searchPw(searchPwDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/certify-pw")
    public ResponseEntity<String> certifyPw(@RequestBody MemberResetPwDTO resetPwDTO) {
        log.info("MemberRestController.certifyPw");
        String result = memberService.checkCno(resetPwDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
