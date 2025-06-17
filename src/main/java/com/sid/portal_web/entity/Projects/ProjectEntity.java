package com.sid.portal_web.entity.Projects;

import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.entity.Foundation.FoundationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    private Integer managemerId;

    private String name;

    private String descripcion;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal budget;

    private Integer developmentId;

    private String statusId;

    @ManyToMany(mappedBy = "projectsInFoundation")
    List<FoundationEntity> foundations;
}
