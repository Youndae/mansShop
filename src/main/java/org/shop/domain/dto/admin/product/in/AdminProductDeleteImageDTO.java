package org.shop.domain.dto.admin.product.in;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductDeleteImageDTO {

    private String deleteFirstThumbnail;

    private List<String> deleteThumbnails;

    private List<String> deleteInfoImages;
}
