package com.sid.portal_web.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "profile")
@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class ProfileEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne
    @MapsId // nos dice que se usa la misma llave primaria
    @JoinColumn(name = "user_id") // la clave foranea
    private UserEntity user;

    private String name;
    private String firstLastName;
    private String secondLastName;
    private String profileImage;
    private String bio;
    private String academic_degree;

}
