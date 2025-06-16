package com.sid.portal_web.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NewsDetailResponse {
    private Long id;
    private String title;
    private String description;
    private String content;
    private String imageUrl;
    private String author;
    private LocalDateTime publishDate;
    private List<String> tags;
}
