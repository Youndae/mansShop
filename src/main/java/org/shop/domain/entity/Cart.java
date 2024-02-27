package org.shop.domain.entity;

import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    private String cartNo;

    private String userId;

    private String ckId;

    private Date createdAt;

    private Date updatedAt;

    public void setCartNo(String cartNo) {
        this.cartNo = cartNo != null ?
                            cartNo : new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()).toString()
                                            + RandomStringUtils.random(4, true, true);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCkId(String ckId) {
        this.ckId = ckId;
    }
}
