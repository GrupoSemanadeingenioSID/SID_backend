package com.sid.portal_web.service.foundation;

import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.entity.FoundationEntity;
import com.sid.portal_web.mapper.foundation.FoundationMapper;
import com.sid.portal_web.repository.foundation.FoundationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoundationServiceImpl implements FoundationService{
    private final FoundationRepository foundationRepository;
    private final FoundationMapper foundationMapper;

    @Override
    public Page<FoundationResponse> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<FoundationEntity> pageEntities = foundationRepository.findAll(pageable);

        return pageEntities
                .map(foundationEntity -> {
                    Foundation domain = foundationMapper.entityToDomain(foundationEntity);
                    return foundationMapper.domainToResponse(domain);
                });
    }
}
