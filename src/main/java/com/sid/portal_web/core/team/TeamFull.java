package com.sid.portal_web.core.team;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TeamFull implements Team {

    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private String leader;
    private List<TeamMember> members;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    @Override
    public String getLeader() {
        return leader;
    }

    @Override
    public List<TeamMember> getMembers() {
        return members;
    }
}
