package com.sid.portal_web.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "social_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialMediaEntity implements Serializable {
    @Id
    @Column(name = "social_media_id")
    private String id;

    private String name;
    private String url;
    private String account;

    @OneToMany(mappedBy = "socialMedia")
    private List<FoundSocialMediaEntity> foundationLinks;
}
