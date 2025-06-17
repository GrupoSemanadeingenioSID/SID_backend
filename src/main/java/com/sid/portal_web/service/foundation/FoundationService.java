package com.sid.portal_web.service.foundation;


import com.sid.portal_web.dto.request.*;
import com.sid.portal_web.dto.response.*;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface FoundationService {
    Page<FoundationResponse> findAll(int page, int pageSize, String sortBy, boolean asc);
    Optional<FoundationContactResponse> findById(int id);
    Optional<FoundationDashboardResponse>findByIdDashboard(int id);
    void createFoundation(FoundationRequest foundationRequest);
    void updateFoundation(Integer idExistingFoundation,FoundationRequest foundationRequest);
    void deleteById(int id);
}
