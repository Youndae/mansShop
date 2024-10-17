package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.myPage.in.MemberCheckRequestDTO;
import org.shop.domain.dto.myPage.in.MemberInfoRequestDTO;
import org.shop.domain.dto.myPage.qna.in.MemberQnARequestDTO;
import org.shop.domain.dto.myPage.qna.in.QnAReplyRequestDTO;
import org.shop.domain.dto.myPage.out.MyPageReviewDetailDTO;
import org.shop.domain.enumeration.Result;
import org.shop.service.CookieService;
import org.shop.service.MyPageService;
import org.shop.service.PrincipalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/my-page")
@RequiredArgsConstructor
@Slf4j
public class MyPageRestController {

    private final PrincipalService principalService;

    private final MyPageService myPageService;

    private final CookieService cookieService;

    @DeleteMapping("/qna/product/{qnaId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> deleteProductQnA(@PathVariable("qnaId") long qnaId
                                            , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.deleteProductQnA(qnaId, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/qna/member")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<Long> insertMemberQnA(@RequestBody MemberQnARequestDTO dto
                                            , Principal principal) {

        String userId = principalService.getUserIdToPrincipal(principal);
        Long result = myPageService.postMemberQnA(dto, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/qna/member/{qnaId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<Long> patchMemberQnA(@PathVariable("qnaId") long qnaId
                                            , @RequestBody MemberQnARequestDTO dto
                                            , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        Long result = myPageService.patchMemberQnA(qnaId, dto, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/qna/member/{qnaId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> deleteMemberQnA(@PathVariable("qnaId") long qnaId
                                            , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.deleteMemberQnA(qnaId, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/qna/member/reply")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> insertMemberQnAReply(@RequestBody QnAReplyRequestDTO dto
                                                        , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.postMemberQnAReply(dto, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/qna/member/reply/{replyId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> patchMemberQnAReply(@PathVariable("replyId") long replyId
                                                 , @RequestBody Map<String, String> contentMap
                                                , Principal principal) {
        QnAReplyRequestDTO dto = new QnAReplyRequestDTO(replyId, contentMap);
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.patchMemberQnAReply(dto, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<MyPageReviewDetailDTO> getReviewDetail(@PathVariable("reviewId") long reviewId
                                                                , Principal principal){
        String userId = principalService.getUserIdToPrincipal(principal);
        MyPageReviewDetailDTO result = myPageService.getReviewDetail(reviewId, userId);

        log.info("MyPageRestController.getReviewDetail :: result : {}", result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/review/{orderDetailId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<?> postReview(@PathVariable("orderDetailId") long orderDetailId
                                        , @RequestBody Map<String, String> content
                                        , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.postReview(orderDetailId, content.get("content"), userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/review/{reviewId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> patchReview(@PathVariable("reviewId") long reviewId
                                            , @RequestBody Map<String, String> contentMap
                                            , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        QnAReplyRequestDTO dto = new QnAReplyRequestDTO(reviewId, contentMap);
        String result = myPageService.patchReview(dto, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/review/{reviewId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") long reviewId
                                            , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.deleteReview(reviewId, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/member-check")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> getMemberInfo(@RequestBody MemberCheckRequestDTO dto
                                                , Principal principal
                                                , HttpServletResponse response) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.checkMember(dto, userId);
        if(result.equals(Result.SUCCESS.getResultKey()))
            cookieService.createMemberCheckCookie(userId, response);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/member/nickname-check")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> checkNickname(@RequestBody Map<String, String> nickname
                                                , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.checkNickname(nickname.get("nickname"), userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/member")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> patchMemberInfo(@RequestBody MemberInfoRequestDTO dto
                                                , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.patchMemberInfo(dto, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/like/{likeId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<String> deleteLike(@PathVariable("likeId") long likeId
                                            , Principal principal) {
        String userId = principalService.getUserIdToPrincipal(principal);
        String result = myPageService.deleteLike(likeId, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
