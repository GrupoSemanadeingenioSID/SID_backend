package com.sid.portal_web.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "profile")
@Data
@RequiredArgsConstructor
public class ProfileEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne
    @MapsId // nos dice que se usa la misma llave primaria
    @JoinColumn(name = "user_id") // la clave foranea
    private UserEntity user;

    private String name;
    private String profile_image;
    private String bio;
    private String academic_degree;

}
