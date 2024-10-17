package org.shop.domain.dto.admin.qna.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.shop.domain.dto.myPage.qna.business.MyPageMemberQnADetailDTO;
import org.shop.domain.dto.myPage.qna.business.QnAReplyListDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class AdminMemberQnADetailDTO {

    private long qnaId;

    private String classification;

    private String title;

    private String writer;

    private String content;

    private LocalDate createdAt;

    private boolean status;

    private List<QnAReplyListDTO> replyList;

    public AdminMemberQnADetailDTO(MyPageMemberQnADetailDTO dto, List<QnAReplyListDTO> replyList) {
        this.qnaId = dto.getQnaId();
        this.classification = dto.getClassification();
        this.title = dto.getTitle();
        this.writer = dto.getWriter();
        this.content = dto.getContent();
        this.createdAt = dto.getUpdatedAt();
        this.status = dto.isStatus();
        this.replyList = replyList;
    }
}
