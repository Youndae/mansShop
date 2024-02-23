package org.shop.domain.entity;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductThumbnail {

    private String thumbNo;

    private String pno;

    private String pThumbnail;

    public ProductThumbnail(String thumbNo, String pno, String pThumbnail) {
        StringBuffer sb = new StringBuffer();
        this.thumbNo = thumbNo != null ? thumbNo : sb.append("s_")
                                                    .append(pno + "_")
                                                    .append(new SimpleDateFormat("yyyyMMddHHmmss")
                                                            .format(System.currentTimeMillis()))
                                                    .append(UUID.randomUUID()).toString();
        this.pno = pno;
        this.pThumbnail = pThumbnail;
    }
}
