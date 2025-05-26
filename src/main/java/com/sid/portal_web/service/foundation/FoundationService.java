package com.sid.portal_web.service.foundation;


import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.entity.FoundationEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoundationService {
    Page<FoundationResponse> findAll(int page, int pageSize);
}
