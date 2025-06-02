package com.sid.portal_web.core;

import java.util.Objects;

public record FoundationContact(
        Integer id,
        String name,
        String phone,
        String address,
        String website,
        String location

) {
    public FoundationContact {
        Objects.requireNonNull(name, "Contact name cannot be null");
        Objects.requireNonNull(phone, "Phone cannot be null");
        Objects.requireNonNull(address, "Address cannot be null");
        Objects.requireNonNull(location, "Location cannot be null");

    }
}
