package com.sid.portal_web.entity.activity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "activity_dev")
@Data
public class ActivityDevEntity {
    @EmbeddedId
    private ActivityDevId id;

    @ManyToOne
    @MapsId("activityId")
    @JoinColumn(name = "activity_id")
    private ActivityEntity activity;

    @ManyToOne
    @MapsId("committeeId")
    @JoinColumn(name = "committee_id")
    private CommitteeEntity committee;

    @Column(nullable = false, length = 50)
    private String description;

}

