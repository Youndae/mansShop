package org.shop.service;

import org.shop.domain.entity.ChatRoom;

import java.security.Principal;

public interface ManagerService {
    public String findByManagerRoomId(Principal principal);

    public ChatRoom findByRoomInfo(String chatRoomId);

}
