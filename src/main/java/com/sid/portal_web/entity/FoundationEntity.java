package com.sid.portal_web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;



@Entity
@Table(name = "foundation")
@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class FoundationEntity implements Serializable {
    @Id
    @Column(name = "foundation_id")
    private Integer id;

    private String name;

    private String description;

    private String logo_url;
}
