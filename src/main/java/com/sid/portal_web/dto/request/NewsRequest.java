package com.sid.portal_web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {
    private String title;
    private String description;
    private String content;
    private String imageUrl;
    private Long memberId;
    private List<String> tags;
}