package org.shop.domain.dto.admin.qna.out;

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
public class AdminProductQnADetailDTO {
    private long qnaId;

    private String title;

    private String writer;

    private String content;

    private LocalDate createdAt;

    private boolean status;

    private List<QnAReplyListDTO> replyList;

    public AdminProductQnADetailDTO(MyPageProductQnADetailDTO dto, List<QnAReplyListDTO> replyList) {
        this.qnaId = dto.getQnaId();
        this.title = dto.getProductName();
        this.writer = dto.getWriter();
        this.content = dto.getContent();
        this.createdAt = dto.getCreatedAt();
        this.status = dto.isStatus();
        this.replyList = replyList;
    }
}
