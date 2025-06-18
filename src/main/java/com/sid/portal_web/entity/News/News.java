package com.sid.portal_web.entity.News;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "img_url", nullable = false)
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime publishDate;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    //Relación con Member (autor de la noticia)
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //Relación muchos a muchos con NewsTopic (categorías)
    @ManyToMany
    @JoinTable(
        name = "news_category_assignement",
        joinColumns = @JoinColumn(name = "news_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<NewsTopic> tags;
}
