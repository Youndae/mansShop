package org.shop.service;

import org.shop.domain.dto.admin.*;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.annotation.ExceptionProxy;

import java.security.Principal;
import java.util.List;

public interface AdminService {

    public String addProduct(ProductInsertDTO dto, MultipartFile firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg);


    public String modifyProduct(ProductModifyDTO modify, MultipartFile firstThumb, List<MultipartFile> thumb
                                , List<MultipartFile> infoImg, String delFirstThumb, List<String> delThumb
                                , List<String> delInfoImg);

    public String addProductOp(ProductOpInsertDTO dto);


    public String qnAReplyProc(MyQnAReplyDTO dto, Principal principal);

    String productQnAReplyProc(ProductQnAReplyDTO dto, Principal principal);

    String qnaReplyDelete(int replyNo);
}
