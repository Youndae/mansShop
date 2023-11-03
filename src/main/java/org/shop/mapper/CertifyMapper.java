package org.shop.mapper;

import org.shop.domain.dto.member.SearchIdDTO;
import org.shop.domain.entity.Certify;

public interface CertifyMapper {

    public int addCertify(Certify certify);

    public int deleteCertify(int cno);

    public int checkCertify(SearchIdDTO dto);
}
