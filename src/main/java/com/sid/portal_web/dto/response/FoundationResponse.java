package com.sid.portal_web.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoundationResponse {
    private Integer id;
    private String name;
    private String description;
    private String logo_url;
}
