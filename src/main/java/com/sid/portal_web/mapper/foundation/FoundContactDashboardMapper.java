package com.sid.portal_web.mapper.foundation;

import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.core.FoundationContact;
import com.sid.portal_web.dto.response.FoundationDashboardResponse;
import com.sid.portal_web.dto.response.SocialMediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FoundContactDashboardMapper {
    public FoundationDashboardResponse domainToResponse(FoundationContact foundationContact
            , Foundation foundation, Integer quantity_proyects, List<SocialMediaResponse> socialMedia){
        return FoundationDashboardResponse
                .builder()
                .id(foundationContact.id())
                .name(foundationContact.name())
                .logo_url(foundation.logo_url())
                .description(foundation.description())
                .address(foundationContact.address())
                .phone(foundationContact.phone())
                .location(foundationContact.location())
                .website(foundationContact.website())
                .quantity_proyects(quantity_proyects)
                .socialMedia(socialMedia)
                .build();
    }
}
