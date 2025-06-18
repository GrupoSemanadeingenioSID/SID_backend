package com.sid.portal_web.mapper;

import com.sid.portal_web.dto.request.NewsRequest;
import com.sid.portal_web.dto.request.NewsUpdateRequest;
import com.sid.portal_web.dto.response.NewsDetailResponse;
import com.sid.portal_web.dto.response.NewsResponse;
import com.sid.portal_web.dto.response.NewsSummaryResponse;
import com.sid.portal_web.entity.News.News;
import com.sid.portal_web.entity.News.NewsTopic;
import com.sid.portal_web.entity.News.Member;

import java.util.List;
import java.util.stream.Collectors;

public class NewsMapper {

    // News -> NewsResponse (general, puede usarse para todo si no se divide)
    public static NewsResponse toDto(News news) {
        return NewsResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .description(news.getDescription())
                .content(news.getContent())
                .publishDate(news.getPublishDate())
                .imageUrl(news.getImageUrl())
                .author(mapAuthor(news.getMember()))
                .tags(mapTags(news.getTags()))
                .build();
    }

    // NewsRequest + objetos -> News (crear)
    public static News toEntity(NewsRequest request, Member member, List<NewsTopic> tags) {
        return News.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .member(member)
                .tags(tags)
                .build();
    }

    //News + NewsUpdateRequest -> actualiza campos
    public static void updateEntity(News news, NewsUpdateRequest request, List<NewsTopic> tags) {
        if (request.getTitle() != null) news.setTitle(request.getTitle());
        if (request.getDescription() != null) news.setDescription(request.getDescription());
        if (request.getContent() != null) news.setContent(request.getContent());
        if (request.getImageUrl() != null) news.setImageUrl(request.getImageUrl());
        if (tags != null) news.setTags(tags);
    }

    //News -> NewsSummaryResponse (para listar noticias)
    public static NewsSummaryResponse toSummaryDto(News news) {
        return NewsSummaryResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .author(mapAuthor(news.getMember()))
                .publishDate(news.getPublishDate())
                .build();
    }

    //News -> NewsDetailResponse (para ver detalle)
    public static NewsDetailResponse toDetailDto(News news) {
        return NewsDetailResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .description(news.getDescription())
                .content(news.getContent())
                .imageUrl(news.getImageUrl())
                .publishDate(news.getPublishDate())
                .author(mapAuthor(news.getMember()))
                .tags(mapTags(news.getTags()))
                .build();
    }

    //para obtener autor (email)
    private static String mapAuthor(Member member) {
        if (member == null || member.getUser() == null) return "Desconocido";
        return member.getUser().getEmail();
    }

    //para obtener lista de nombres de tags
    private static List<String> mapTags(List<NewsTopic> tags) {
        if (tags == null) return List.of();
        return tags.stream()
                .map(NewsTopic::getName)
                .collect(Collectors.toList());
    }
}
