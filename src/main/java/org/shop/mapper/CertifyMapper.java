package org.shop.mapper;

import org.shop.domain.dto.member.SearchIdDTO;
import org.shop.domain.entity.Certify;

/**
 * Redis로 전환
 */
@Deprecated
public interface CertifyMapper {

    public int addCertify(Certify certify);

    public int deleteCertify(int cno);

    public int checkCertify(SearchIdDTO dto);
}
