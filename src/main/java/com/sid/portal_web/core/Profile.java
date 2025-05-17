package com.sid.portal_web.core;

public record Profile(
        Integer id,
        String name,
        String firsLastName,
        String secondLastName,
        String image,
        String bio,
        String academicDegree) {

}
