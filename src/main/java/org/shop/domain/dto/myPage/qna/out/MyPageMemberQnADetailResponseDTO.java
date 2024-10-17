package org.shop.domain.dto.myPage.qna.out;

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
public class MyPageMemberQnADetailResponseDTO {

    private long qnaId;

    private String classification;

    private String title;

    private String writer;

    private String content;

    private LocalDate date;

    private boolean status;

    private List<QnAReplyListDTO> replyList;

    public MyPageMemberQnADetailResponseDTO(MyPageMemberQnADetailDTO content, List<QnAReplyListDTO> replyList) {
        this.qnaId = content.getQnaId();
        this.classification = content.getClassification();
        this.title = content.getTitle();
        this.writer = content.getWriter();
        this.content = content.getContent();
        this.date = content.getUpdatedAt();
        this.status = content.isStatus();
        this.replyList = replyList;
    }
}
