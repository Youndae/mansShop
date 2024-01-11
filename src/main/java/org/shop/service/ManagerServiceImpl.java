package org.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.domain.entity.ChatRoom;
import org.shop.mapper.ManagerMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{

    private final ManagerMapper managerMapper;

    @Override
    public String findByManagerRoomId(Principal principal) {

        ChatRoom chatRoom = ChatRoom.builder()
                .managerId(principal.getName())
                .build();

        managerMapper.connectManager(chatRoom);

        String roomId = chatRoom.getChatRoomId();

        log.info("manager service roomId : " + roomId);

        return roomId;
    }

    @Override
    public ChatRoom findByRoomInfo(String chatRoomId) {
        return null;
    }
}
