package com.sid.portal_web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;


@Table(name = "team")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer teamId;

    private String name; // nombre del equipo
    private String description; // descripcion del equipo
    private String teamType; // Tipo de equipo
    private Boolean active; // Estado del equipo , si es activbo o no
    private Date formationDate; // formacion del equipo
    private Integer projectId; // Relación no mapeada. Proyecto al que pertenece el equipo.


}
