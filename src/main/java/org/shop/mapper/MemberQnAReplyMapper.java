package org.shop.mapper;

import org.shop.domain.dto.myPage.qna.business.QnAReplyListDTO;
import org.shop.domain.entity.MemberQnAReply;

import java.util.List;

public interface MemberQnAReplyMapper {
    List<QnAReplyListDTO> findAllByQnAId(long qnaId);

    MemberQnAReply findById(long id);

    void patchContent(MemberQnAReply entity);

    void insertReply(MemberQnAReply entity);
}
