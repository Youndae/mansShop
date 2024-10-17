package org.shop.mapper;

import org.shop.domain.dto.myPage.qna.business.QnAReplyListDTO;
import org.shop.domain.dto.myPage.qna.in.QnAReplyRequestDTO;
import org.shop.domain.entity.ProductQnAReply;

import java.util.List;

public interface ProductQnAReplyMapper {
    List<ProductQnAReply> findAllByQnAIds(List<Long> qnaIds);

    List<QnAReplyListDTO> findAllByQnAId(long qnaId);

    void insertReply(ProductQnAReply entity);

    void patchReply(QnAReplyRequestDTO replyDTO);
}
