package com.sid.portal_web.entity.activity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activity_members")
@Data
public class ActivityMembersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_member")
    private Integer activityMemberId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String id;

    @OneToMany(mappedBy = "activityMember")
    private Set<ActivityParticipationEntity> activityParticipation = new HashSet<>();

}
