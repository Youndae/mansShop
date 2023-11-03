package org.shop.service;

import org.shop.domain.dto.admin.*;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.annotation.ExceptionProxy;

import java.security.Principal;
import java.util.List;

public interface AdminService {

    public int addProduct(ProductInsertDTO dto, MultipartFile firstThumb, List<MultipartFile> thumb, List<MultipartFile> infoImg) throws Exception;


    public int modifyProduct(ProductModifyDTO modify, MultipartFile firstThumb, List<MultipartFile> thumb
                                , List<MultipartFile> infoImg, String delFirstThumb, List<String> delThumb
                                , List<String> delInfoImg) throws Exception;

    public int addProductOp(ProductOpInsertDTO dto) throws Exception;


    public int qnAReplyProc(MyQnAReplyDTO dto, Principal principal) throws Exception;

    int productQnAReplyProc(ProductQnAReplyDTO dto, Principal principal) throws Exception;

    int qnaReplyDelete(int replyNo) throws Exception;
}
