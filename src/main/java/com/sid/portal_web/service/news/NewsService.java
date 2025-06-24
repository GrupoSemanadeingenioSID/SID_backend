package com.sid.portal_web.service.news;

import com.sid.portal_web.dto.request.NewsRequest;
import com.sid.portal_web.dto.response.NewsResponse;

import java.util.List;

public interface NewsService {
    NewsResponse createNews(NewsRequest request);
    List<NewsResponse> getAllNews();
    NewsResponse getNewsById(Long id);
    NewsResponse updateNews(Long id, NewsRequest request);
    void deleteNews(Long id);
    List<NewsResponse> searchByTitle(String keyword);
}
