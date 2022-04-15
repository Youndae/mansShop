package org.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVO {

    private String cartNo;
    private String userId;
    private String ckId;
    private Date createdAt;
    private Date updatedAt;

}
