package com.sid.portal_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoundationDashboardResponse {
    private Integer id;
    private String name;
    private String description;
    private String logo_url;
    private String phone;
    private String address;
    private String website;
    private String location;
    private List<SocialMediaResponse> socialMedia;
    private Integer quantity_proyects;
}
