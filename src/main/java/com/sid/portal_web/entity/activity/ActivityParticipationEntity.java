package com.sid.portal_web.entity.activity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="activity_participation")
@Data
public class ActivityParticipationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="participation_id")
    private Integer participationId;

    @Column(name="ismember", nullable=false)
    private boolean isMember;

    @ManyToOne
    @JoinColumn(name = "title_id", nullable = false)
    private ActivityTitleEntity title;

    @ManyToOne
    @JoinColumn(name = "activity_member", nullable = false)
    private ActivityMembersEntity activityMember;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private ActivityEntity activity;
}
