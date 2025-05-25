package com.sid.portal_web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamRequest {

    private Integer projectId;
    private String name;
    private String description;
    private Date formation_date;
    private boolean active;
    private String leader;
    private List<MembersRequest> members;

}
