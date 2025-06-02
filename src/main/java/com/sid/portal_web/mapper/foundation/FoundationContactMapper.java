package com.sid.portal_web.mapper.foundation;

import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.core.FoundationContact;
import com.sid.portal_web.dto.response.FoundationContactResponse;
import com.sid.portal_web.dto.response.SocialMediaResponse;
import com.sid.portal_web.entity.FoundationContactEntity;
import com.sid.portal_web.entity.FoundationEntity;
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
                entity.getAddress(),
                entity.getPhone(),
                entity.getLocation(),
                entity.getWebsite()
        );
    }
    public FoundationContactResponse domainToResponse(FoundationContact foundationContact, List<SocialMediaResponse> socialMedia){
        return FoundationContactResponse
                .builder()
                .id(foundationContact.id())
                .name(foundationContact.name())
                .address(foundationContact.address())
                .phone(foundationContact.phone())
                .location(foundationContact.location())
                .website(foundationContact.website())
                .socialMedia(socialMedia)
                .build();
    }
}
