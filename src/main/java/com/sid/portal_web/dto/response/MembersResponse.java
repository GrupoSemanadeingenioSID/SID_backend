package com.sid.portal_web.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class MembersResponse {

    private String name;
    private String identify;
    private boolean isMember;

}
