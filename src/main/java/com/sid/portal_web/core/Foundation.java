package com.sid.portal_web.core;
import java.util.Objects;

public record Foundation(
        Integer id,
        String name,
        String description,
        String logo_url) {

    public Foundation {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(description, "Description cannot be null");
        Objects.requireNonNull(logo_url, "Logo URL cannot be null");

        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        if (!isValidUrl(logo_url)) {
            throw new IllegalArgumentException("Logo URL is not valid");
        }
    }

    private static boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}