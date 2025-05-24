package com.sid.portal_web.core;




public record TeamBase(
        Integer id,
        String name,
        String description,
        boolean active,
        String leader
) {
}
