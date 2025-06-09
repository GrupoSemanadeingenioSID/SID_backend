package com.sid.portal_web.dto.request;

import java.util.List;

public class NewsRequest {
    private String title;
    private String description;
    private String content;
    private String imageUrl;
    private Long memberId; // ID del autor
    private List<String> tags; // Lista de ID de categorías

    // Getters y Setters

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
