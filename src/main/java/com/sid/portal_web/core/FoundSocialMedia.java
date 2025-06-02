package com.sid.portal_web.core;

import java.util.Objects;

public record FoundSocialMedia(
        Long id,
        String foundationName,
        SocialMedia socialMedia
) {
    public FoundSocialMedia {
        Objects.requireNonNull(foundationName, "Foundation name cannot be null");
        Objects.requireNonNull(socialMedia, "Social media cannot be null");
    }
}
