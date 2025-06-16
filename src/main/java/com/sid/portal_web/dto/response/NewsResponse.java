package com.sid.portal_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {
    private Long id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime publishDate;
    private String author;
    private String imageUrl;
    private List<String> tags;
}
