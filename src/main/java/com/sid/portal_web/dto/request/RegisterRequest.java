package com.sid.portal_web.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String name;
    private String first_lastName;
    private String second_lastName;
    private String institutionalEmail;
    private String password;

}
