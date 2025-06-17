package com.sid.portal_web.entity.Foundation;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "foundation_contact")
@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class FoundationContactEntity {
    @Id
    @Column(name = "foundation_id")
    private Integer id;

    private String name;

    private String phone;

    private String address;

    private String website;

    private String location;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "foundation_id")
    private FoundationEntity foundation;

    @OneToMany(mappedBy = "foundationsContact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoundSocialMediaEntity> foundationSocial;
}
