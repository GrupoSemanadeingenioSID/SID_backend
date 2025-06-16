package com.sid.portal_web.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoundationRequest {
    private String name;
    private String description;
    private String logo_url;
    private String phone;
    private String address;
    private String location;
    private String website;
    private List<String> socialMediaIds;
}
