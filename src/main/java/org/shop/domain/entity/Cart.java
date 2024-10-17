package org.shop.domain.entity;

import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    private Long id;

    private String userId;

    private String cookieId;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
