package com.sid.portal_web.mapper.foundation;

import com.sid.portal_web.core.SocialMedia;
import com.sid.portal_web.dto.response.SocialMediaResponse;
import com.sid.portal_web.entity.Foundation.SocialMediaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialMediaMapper {

    public SocialMedia entityToDomain(SocialMediaEntity socialMedia) {
        return new SocialMedia(
                socialMedia.getId(),
                socialMedia.getName(),
                socialMedia.getUrl(),
                socialMedia.getAccount()
        );
    }

    public SocialMediaEntity domainToEntity(SocialMedia socialMedia) {
        return SocialMediaEntity
                .builder()
                .id(socialMedia.id())
                .name(socialMedia.name())
                .url(socialMedia.url())
                .account(socialMedia.account())
                .build();
    }

    public SocialMediaResponse domainToResponse(SocialMedia socialMedia) {
        return SocialMediaResponse
                .builder()
                .id(socialMedia.id())
                .name(socialMedia.name())
                .url(socialMedia.url())
                .account(socialMedia.account())
                .build();
    }
}
