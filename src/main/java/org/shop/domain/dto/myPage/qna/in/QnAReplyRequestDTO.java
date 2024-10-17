package org.shop.domain.dto.myPage.qna.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.entity.MemberQnAReply;
import org.shop.domain.entity.ProductQnAReply;

import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QnAReplyRequestDTO {

    private long id;

    private String content;

    public QnAReplyRequestDTO(long id, Map<String, String> requestMap) {
        this.id = id;
        this.content = requestMap.get("content");
    }

    public ProductQnAReply toProductQnAReplyEntity(String userId) {
        return ProductQnAReply.builder()
                .userId(userId)
                .qnaId(this.id)
                .replyContent(this.content)
                .build();
    }

    public MemberQnAReply toMemberQnAReplyEntity(String userId) {
        return MemberQnAReply.builder()
                .userId(userId)
                .qnaId(this.id)
                .replyContent(this.content)
                .build();
    }
}
