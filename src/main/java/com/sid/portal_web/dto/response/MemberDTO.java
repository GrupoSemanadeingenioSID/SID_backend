package com.sid.portal_web.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class MemberDTO {
    private String name;
    private String identify;
    private Boolean isMember;
}
