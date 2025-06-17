package com.sid.portal_web.entity.Foundation;

import com.sid.portal_web.entity.Projects.ProjectEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "project_foundation",
            joinColumns = @JoinColumn(name = "foundation_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<ProjectEntity> projectsInFoundation;
}
