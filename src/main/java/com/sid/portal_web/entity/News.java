package com.sid.portal_web.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//Representacion de la tabla 'News' de la BD
@Entity
@Table(name = "news")
public class News {

     //Generar automaticamente ID de una noticia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    //Campos normales de la noticia
    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "img_url", nullable = false)
    private String imageUrl;

     //Solicitud de JSON " publishDate ": string ( ISO -8601)
    @Column(name = "created_at")
    private LocalDateTime publishDate; //Para convertir y comparar fechas

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    // Relación con Member (quien publicó la noticia)
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Tags (relación muchos a muchos con news_topic)
    @ManyToMany
    @JoinTable(
        name = "news_category_assignement",
        joinColumns = @JoinColumn(name = "news_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<NewsTopic> tags;

    
    //Getters y Setters
    public Long getId() {
     return id;
    }

    public void setId(Long id) {
     this.id = id;
    }

    public String getTitle() {
     return title;
    }

    public void setTitle(String title) {
     this.title = title;
    }

    public String getDescription() {
     return description;
    }

    public void setDescription(String description) {
     this.description = description;
    }

    public String getImageUrl() {
     return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
     this.imageUrl = imageUrl;
    }

    public LocalDateTime getPublishDate() {
     return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
     this.publishDate = publishDate;
    }

    public String getContent() {
     return content;
    }

    public void setContent(String content) {
     this.content = content;
    }

    public Member getMember() {
     return member;
    }

    public void setMember(Member member) {
     this.member = member;
    }

    public List<NewsTopic> getTags() {
     return tags;
    }

    public void setTags(List<NewsTopic> tags) {
     this.tags = tags;
    }

    // Getters y Setters...
}

