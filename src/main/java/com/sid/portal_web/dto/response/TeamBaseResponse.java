package com.sid.portal_web.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamBaseResponse {
    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private String leader;

}
