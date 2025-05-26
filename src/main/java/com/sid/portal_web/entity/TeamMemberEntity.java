package com.sid.portal_web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "team_members")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamMemberEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Integer teamMemberId;

//    @Column(name = "join_date")
    private Date joindDate; // fecha de llegada
    private Date endDate; // fecha de salida
    private Integer rolId; // rol dentro del equipo -> El que hace algo xd
    private Integer teamId; // equipo al cual pertenece el miembro
    private Integer developmentMemberId; // mapeo con el miembro del development
    private Integer teamTitleId; // titulo dentro del equipo -> Lider / miembro / colider


}
