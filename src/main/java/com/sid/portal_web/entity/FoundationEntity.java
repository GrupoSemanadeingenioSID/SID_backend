package com.sid.portal_web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;


@Entity
@Table(name = "foundation")
@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class FoundationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foundation_id")
    private Integer id;

    private String name;

    private String description;

    private String logo_url;
}
