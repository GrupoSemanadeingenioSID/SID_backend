package com.sid.portal_web.entity.News;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "news_topic")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsTopic {

    @Id
    @Column(name = "category_id", length = 50)
    private String id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;
}
