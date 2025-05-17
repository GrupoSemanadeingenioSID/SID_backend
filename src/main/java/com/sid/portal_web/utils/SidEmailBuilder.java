package com.sid.portal_web.utils;

public class SidEmailBuilder {

    private final String email;

    private SidEmailBuilder(String name, String first_lastName, String second_lastName, String institutionalEmail) {
        this.email = name.toLowerCase().charAt(0)
                     + first_lastName.toLowerCase()
                + second_lastName.toLowerCase().charAt(0)
                + institutionalEmail.toLowerCase().substring(0,2)
                + "@sid.com";
    }

    public static String withEmail(String name, String first_lastName, String second_lastName, String institutionalEmail) {
        var instance = new SidEmailBuilder(
                name,
                first_lastName,
                second_lastName,
                institutionalEmail);
        return instance.email;
    }


}
