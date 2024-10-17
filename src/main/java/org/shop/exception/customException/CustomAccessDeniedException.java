package org.shop.exception.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.shop.exception.enumeration.ErrorCode;

@Getter
@AllArgsConstructor
public class CustomAccessDeniedException extends RuntimeException {
    ErrorCode errorCode;

    String message;
}
