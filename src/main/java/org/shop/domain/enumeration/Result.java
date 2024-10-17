package org.shop.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Result {
    SUCCESS("SUCCESS"),
    FAIL("FAIL"),
    DUPLICATE("DUPLICATE"),
    OK("OK"),
    ERROR("ERROR"),
    NOTFOUND("NOTFOUND");

    private final String resultKey;
}
