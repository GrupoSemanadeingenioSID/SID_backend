package com.sid.portal_web.entity.activity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ActivityDevId implements Serializable {
    @Column(name = "activity_id")
    private Integer activityId;

    @Column(name = "committee_id")
    private Integer committeeId;
}
