package com.sid.portal_web.core;

import java.util.Objects;

public record Members(
        Integer activityMemberId,
        String name,
        String identify
) {
    public Members{
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(identify, "identify cannot be null");
    }
}
