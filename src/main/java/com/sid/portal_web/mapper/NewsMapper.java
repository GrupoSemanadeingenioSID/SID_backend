package com.sid.portal_web.mapper;

import com.sid.portal_web.dto.request.NewsRequest;
import com.sid.portal_web.dto.response.NewsResponse;
import com.sid.portal_web.entity.Member;
import com.sid.portal_web.entity.News;
import com.sid.portal_web.entity.NewsTopic;

import java.util.List;
import java.util.stream.Collectors;

public class NewsMapper {

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

    // Métodos auxiliares

    private static String mapAuthor(Member member) {
        if (member == null || member.getUser() == null) return "Desconocido";
        return member.getUser().getEmail();
    }

    private static List<String> mapTags(List<NewsTopic> tags) {
        if (tags == null) return List.of();
        return tags.stream()
                .map(NewsTopic::getName)
                .collect(Collectors.toList());
    }
}
