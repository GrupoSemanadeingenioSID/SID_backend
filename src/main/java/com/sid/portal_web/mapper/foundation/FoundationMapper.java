package com.sid.portal_web.mapper.foundation;


import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.dto.request.FoundationRequest;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.entity.Foundation.FoundationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoundationMapper {

    public Foundation entityToDomain(FoundationEntity foundation){
        return new Foundation(
                foundation.getId(),
                foundation.getName(),
                foundation.getDescription(),
                foundation.getLogo_url()
        );
    }

    public FoundationEntity domainToEntity(Foundation foundation){
        return FoundationEntity
                .builder()
                .id(foundation.id())
                .name(foundation.name())
                .description(foundation.description())
                .logo_url(foundation.logo_url())
                .build();
    }

    public Foundation requestToDomain(FoundationRequest foundationRequest){
        return new Foundation(
                null,
                foundationRequest.getName(),
                foundationRequest.getDescription(),
                foundationRequest.getLogo_url()
        );
    }

    public FoundationResponse domainToResponse(Foundation foundation){
        return FoundationResponse
                .builder()
                .id(foundation.id())
                .name(foundation.name())
                .description(foundation.description())
                .logo_url(foundation.logo_url())
                .build();
    }
}
