package org.shop.mapper;


import org.shop.domain.entity.ChatRoom;

public interface ChatMapper {

    //사용자의 상담 요청으로 방 생성
    public void createChatRoom(ChatRoom chatRoom);

    //채팅방에 접근하려는 사용자가 일치하는지 체크. count(*) 조회 후 int 리턴
    public int checkUser(ChatRoom chatRoom);

    public String duplicationCheck(String uid);

}
