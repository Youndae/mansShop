package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;

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
        this.cartNo = cartNo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCkId(String ckId) {
        this.ckId = ckId;
    }
}
