package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    private String chatRoomId;

    private String userId;

    private String managerId;

    private Date createdAt;

    private int chatStatus;


}
