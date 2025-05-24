package com.sid.portal_web.core.team;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class TeamProxy implements Team {

    private final Integer id;
    private final String name;
    private final String description;
    private final Boolean active;
    private final String leader;

    // Referencias para lazy loading
    private List<TeamMember> members;
    @Getter
    private boolean membersLoaded = false;
    @Setter
    private Supplier<List<TeamMember>> team;

    public TeamProxy(Integer id, String name, String description,
                     Boolean active, String leader) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.leader = leader;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Boolean getActive() {
        return this.active;
    }

    @Override
    public String getLeader() {
        return this.leader;
    }

    @Override
    public List<TeamMember> getMembers() {
        if (team == null) {
            return null;
        }
        else if (!membersLoaded) {
            loadMembers();
        }
        return this.members;
    }


    private void loadMembers() {
        members = team.get();
        membersLoaded = true;
    }
}