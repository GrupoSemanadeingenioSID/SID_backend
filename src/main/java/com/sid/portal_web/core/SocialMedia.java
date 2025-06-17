package com.sid.portal_web.core;

import java.util.Objects;

public record SocialMedia(
        String id,
        String name,
        String url,
        String account
) {
    public SocialMedia {
        Objects.requireNonNull(id, "Social media ID cannot be null");
        Objects.requireNonNull(name, "Social media name cannot be null");
        Objects.requireNonNull(url, "Social media URL cannot be null");
        Objects.requireNonNull(account, "Account cannot be null");

        if (!isValidUrl(url)) {
            throw new IllegalArgumentException("URL is not valid");
        }
    }

    private static boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}
