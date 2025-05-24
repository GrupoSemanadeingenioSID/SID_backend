package com.sid.portal_web.dto.response;


import com.sid.portal_web.core.team.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponse {
    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private String leader;
    private List<TeamMember> members;
}
