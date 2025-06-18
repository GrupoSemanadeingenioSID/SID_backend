package com.sid.portal_web.entity.News;

import com.sid.portal_web.entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // Relación con UserEntity
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
