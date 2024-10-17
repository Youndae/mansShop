package org.shop.domain.entity;

import lombok.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private String id;

    private String classificationId;

    private String productName;

    private int productPrice;

    private String thumbnail;

    private long productSales;

    private int productDiscount;

    private boolean isOpen;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
