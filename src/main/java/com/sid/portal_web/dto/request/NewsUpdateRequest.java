package com.sid.portal_web.dto.request;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class NewsUpdateRequest {
    private String title;
    private String description;
    private String content;
    private String imageUrl;
    private List<String> tags;
}