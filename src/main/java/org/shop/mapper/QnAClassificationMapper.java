package org.shop.mapper;

import org.apache.ibatis.annotations.Param;
import org.shop.domain.entity.QnAClassification;

import java.util.List;

public interface QnAClassificationMapper {

    List<QnAClassification> findAll();

    void insertClassification(@Param("name") String content);

    void deleteClassification(@Param("id") long classificationId);
}
