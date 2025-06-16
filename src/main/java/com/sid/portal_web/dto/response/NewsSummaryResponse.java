package com.sid.portal_web.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NewsSummaryResponse {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime publishDate;
}
