package org.shop.domain.dto.myPage.qna.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.myPage.qna.business.MyPageProductQnADetailDTO;
import org.shop.domain.dto.myPage.qna.business.QnAReplyListDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class MyPageProductQnADetailResponseDTO {
    private long qnaId;

    private String productId;

    private String title;

    private String writer;

    private String content;

    private LocalDate date;

    private boolean status;

    private List<QnAReplyListDTO> replyList;

    public MyPageProductQnADetailResponseDTO(MyPageProductQnADetailDTO content, List<QnAReplyListDTO> replyList) {
        this.qnaId = content.getQnaId();
        this.productId = content.getProductId();
        this.title = content.getProductName();
        this.writer = content.getWriter();
        this.content = content.getContent();
        this.date = content.getCreatedAt();
        this.status = content.isStatus();
        this.replyList = replyList;
    }
}
