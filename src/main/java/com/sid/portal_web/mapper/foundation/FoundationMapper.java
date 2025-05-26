package com.sid.portal_web.mapper.foundation;


import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.entity.FoundationEntity;
import com.sid.portal_web.entity.UserEntity;
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
