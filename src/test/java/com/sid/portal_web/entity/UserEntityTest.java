package com.sid.portal_web.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void getId() {
        UserEntity user = new UserEntity();
        user.setId(42);
        assertEquals(42, user.getId());
    }

    @Test
    void getEmail() {
        UserEntity user = new UserEntity();
        user.setEmail("user@domain.com");
        assertEquals("user@domain.com", user.getEmail());
    }

    @Test
    void isActive() {
        UserEntity user = new UserEntity();
        user.setActive(false);
        assertFalse(user.isActive());
    }
}
