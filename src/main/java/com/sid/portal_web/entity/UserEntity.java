package com.sid.portal_web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;



@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    private String email;
    private String institutionalEmail;
    private String password;
    private boolean active;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProfileEntity profile;

}
