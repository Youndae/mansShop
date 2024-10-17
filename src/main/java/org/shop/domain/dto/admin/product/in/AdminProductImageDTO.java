package org.shop.domain.dto.admin.product.in;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminProductImageDTO {
    private MultipartFile firstThumbnail;

    private List<MultipartFile> thumbnails;

    private List<MultipartFile> infoImages;
}
