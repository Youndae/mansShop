package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.paging.Criteria;
import org.shop.domain.dto.paging.PageDTO;
import org.shop.mapper.AdminMapper;
import org.shop.service.ManagerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/managerPage")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_MANAGER')")
@Slf4j
public class ManagerController {

    private final ManagerService managerService;

    private final AdminMapper adminMapper;
    /**
     * 매니저 페이지 기능
     * 채팅상담(버튼 누르면 바로 미상담 채팅으로 입장, 추후 알림 추가)
     * 전체 채팅 내역(상담 내역) - 리스트, 내역
     * 주문목록 - 관리자와 동일한 구조로
     */

    //주문목록. 매니저가 로그인하면 가장 첫 페이지
    @GetMapping("/orderList")
    public String managerMain(Model model, Criteria cri){

        model.addAttribute("order", adminMapper.orderList(cri));

        int total = adminMapper.getOrderTotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));

        return "manager/managerOrderList";
    }

    // 채팅상담. 채팅방을 찾아 상담할 방이 있는지 결과 전달
    @GetMapping("/getChatRoom")
    @ResponseBody
    public String connectChatRoom(Model model, Principal principal){
        log.info("manager get chatRoom");

        String result = managerService.findByManagerRoomId(principal);

        log.info("manager get ChatRoom result : " + result);

        return result;
    }

    //채팅 시작.
    @GetMapping("/chatRoom/{chatRoomId}")
    public String connectRoom(@PathVariable("chatRoomId") String chatRoomId, Model model) {

        log.info("manager start chat");

        //채팅방 id 전달
        model.addAttribute("room", chatRoomId);

        //추후 채팅 내역 저장 처리 후 채팅 내역 데이터 전달 추가.

        return "chat/room";
    }

    //채팅 리스트(완료된 모든 채팅 상담의 리스트 출력)
    @GetMapping("/chatList")
    public String chatList(Model model) {
        //채팅 리스트 조회
        model.addAttribute("chatList", null);

        return "manager/chatList";
    }

    //채팅 내역(리스트에서 채팅을 눌러 내용 확인)
    @GetMapping("/chatDetail/{chatRoomId}")
    public String chatDetail(@PathVariable("chatRoomId") String chatRoomId, Model model){
        //채팅 내역
        model.addAttribute("chatContent", null);

        return "manager/chatDetail";
    }
}
