package org.shop.mapper;

import org.shop.domain.entity.Classification;

import java.util.List;

public interface ClassificationMapper {

    List<String> findAllId();

    List<Classification> findAllIdOrderByClassificationStep();


}
