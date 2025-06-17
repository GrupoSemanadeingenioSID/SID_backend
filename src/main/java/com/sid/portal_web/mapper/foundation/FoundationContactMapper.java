package com.sid.portal_web.mapper.foundation;

import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.core.FoundationContact;
import com.sid.portal_web.dto.request.FoundationRequest;
import com.sid.portal_web.dto.response.FoundationContactResponse;
import com.sid.portal_web.dto.response.SocialMediaResponse;
import com.sid.portal_web.entity.Foundation.FoundationContactEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FoundationContactMapper {
    public FoundationContact entityToDomain(FoundationContactEntity entity) {
        return new FoundationContact(
                entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getWebsite(),
                entity.getLocation()
        );
    }

    public void updateEntityFromDomain(FoundationContactEntity existing, FoundationContact updated) {
        existing.setName(updated.name());
        existing.setPhone(updated.phone());
        existing.setAddress(updated.address());
        existing.setWebsite(updated.website());
        existing.setLocation(updated.location());
    }

    public FoundationContactEntity domainToEntity(FoundationContact foundationContact){
        return FoundationContactEntity
                .builder()
                .name(foundationContact.name())
                .address(foundationContact.address())
                .phone(foundationContact.phone())
                .location(foundationContact.location())
                .website(foundationContact.website())
                .build();
    }

    public FoundationContact requestToDomain(FoundationRequest foundationRequest){
        return new FoundationContact(
                null,
                foundationRequest.getName(),
                foundationRequest.getPhone(),
                foundationRequest.getAddress(),
                foundationRequest.getWebsite(),
                foundationRequest.getLocation()
        );
    }

    public FoundationContactResponse domainToResponse(FoundationContact foundationContact,
                                                      List<SocialMediaResponse> socialMedia
    , Foundation foundation){
        return FoundationContactResponse
                .builder()
                .id(foundationContact.id())
                .name(foundationContact.name())
                .logo_url(foundation.logo_url())
                .description(foundation.description())
                .address(foundationContact.address())
                .phone(foundationContact.phone())
                .location(foundationContact.location())
                .website(foundationContact.website())
                .socialMedia(socialMedia)
                .build();
    }
}
