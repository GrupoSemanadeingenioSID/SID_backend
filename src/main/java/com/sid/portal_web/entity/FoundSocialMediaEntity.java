    package com.sid.portal_web.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.io.Serializable;

    @Entity
    @Table(name = "found_social_media")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class FoundSocialMediaEntity implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "found_social_media_id")
        private Integer foundSocialMediaId;

        @ManyToOne
        @JoinColumn(name = "social_media_id")
        private SocialMediaEntity socialMedia;

        @ManyToOne
        @JoinColumn(name = "foundation_id")
        private FoundationContactEntity foundations;
    }

