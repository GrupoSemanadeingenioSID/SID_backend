package com.sid.portal_web.core;

import java.util.Objects;

public record Committees(
        Integer committeeId,
        String name,
        String description
) {
    public Committees {
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(description, "description cannot be null");
    }
}
