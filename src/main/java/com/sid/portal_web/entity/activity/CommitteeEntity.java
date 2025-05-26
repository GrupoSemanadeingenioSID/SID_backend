package com.sid.portal_web.entity.activity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Table(name = "committee")
@Data
public class CommitteeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "committee_id")
    private Integer committeeId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String objectives;

    @Column(name="start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;
}