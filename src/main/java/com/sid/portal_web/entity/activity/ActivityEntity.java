package com.sid.portal_web.entity.activity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * Representa una entidad de actividad en el sistema.
 * Almacena información sobre tareas, proyectos o actividades a realizar.
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
    private Set<ActivityDevEntity> activityDev;

    @OneToMany(mappedBy = "activity")
    private Set<ActivityParticipationEntity> activityParticipation;


}
