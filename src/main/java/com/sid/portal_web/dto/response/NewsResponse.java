package com.sid.portal_web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class NewsResponse {
    private Long id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime publishDate;
    private String author;
    private String imageUrl;
    private List<String> tags; // Nombres de las categorías

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDateTime publishDate) { this.publishDate = publishDate; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}

