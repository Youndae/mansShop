package org.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.dto.chat.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO messageDTO){
        log.info("/chat/enter message : " + messageDTO);


        template.convertAndSend("/sub/chat/room/" + messageDTO.getRoomId(), messageDTO);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO messageDTO) {
        log.info("/chat/message message : " + messageDTO);

        template.convertAndSend("/sub/chat/room/" + messageDTO.getRoomId(), messageDTO);
    }
}
