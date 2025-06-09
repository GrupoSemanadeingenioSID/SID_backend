package com.sid.portal_web.entity;

import jakarta.persistence.*;


//Vinculación de la clase con la tabla 'members'
@Entity
@Table(name = "members")
public class Member {

     //Automatizacion del ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Long getId() {
     return id;
    }

    public void setId(Long id) {
     this.id = id;
    }

    public UserEntity getUser() {
     return user;
    }

    public void setUser(UserEntity user) {
     this.user = user;
    }
    
    // Getters y Setters
}

