package org.shop.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //manager의 채팅 상담 요청 시 채팅방이 없을 때 ExecutorException이 발생.
    //동적쿼리의 selectKey가 반환하는 데이터가 없기 때문에 발생하는 예외
    //이걸 분리해서 조회한다음 업데이트 처리하자니 동시에 요청이 발생하는 경우를 방지하기 위해 조금 애매..
    //다른 해결방법 모색 필요.
    @ExceptionHandler(ExecutorException.class)
    public String executorExceptionHandler(Exception e){
        log.error("ExecutorException : " + e.getMessage());

        return null;
    }
}
