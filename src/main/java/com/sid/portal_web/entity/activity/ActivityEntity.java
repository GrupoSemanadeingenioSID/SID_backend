package com.sid.portal_web.entity.activity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa una entidad de actividad
 **/

@Entity
@Table (name = "activities")
@Data
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Integer activityId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Status status;

    @Column(name = "total_hours")
    private Integer totalHours;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    public enum Priority {
        ALTA,
        MEDIA,
        BAJA
    }

    public enum Status {
        ACTIVA,
        ESPERA,
        COMPLETADA,
        CANCELADA
    }


    @OneToMany(mappedBy = "activity")
    private List<ActivityDevEntity> activityDev;

    @OneToMany(mappedBy = "activity")
    private List<ActivityParticipationEntity> activityParticipation;


}
