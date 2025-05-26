package com.sid.portal_web.entity.activity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activity_title")
@Data
public class ActivityTitleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Integer titleId;

    @Column(nullable = false, length = 50)
    private String description;

    @OneToMany(mappedBy = "title")
    private Set<ActivityParticipationEntity> activityParticipation = new HashSet<>();

}
