package com.sid.portal_web.service.foundation;


import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.dto.response.FoundationContactResponse;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.entity.FoundationEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FoundationService {
    Page<FoundationResponse> findAll(int page, int pageSize, String sortBy, boolean asc);
    Optional<FoundationContactResponse> findById(int id);
    void deleteById(int id);
}
