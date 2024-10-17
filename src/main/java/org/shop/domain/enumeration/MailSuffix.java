package org.shop.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum MailSuffix {

    NAVER("naver.com", "네이버")
    , DAUM("daum.net", "다음")
    , GOOGLE("gmail.com", "구글")
    , NONE("none", "직접입력");

    private final String mailSuffixType;

    private final String mailSuffixTypeStr;

    public static String findSuffixType(String suffix) {

        return Arrays.stream(MailSuffix.values())
                .filter(mailSuffix -> mailSuffix.mailSuffixType.equals(suffix))
                .findFirst()
                .orElse(NONE)
                .mailSuffixType;
    }
}
