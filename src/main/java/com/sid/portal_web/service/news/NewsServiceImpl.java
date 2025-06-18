package com.sid.portal_web.service.news;

import com.sid.portal_web.dto.request.NewsRequest;
import com.sid.portal_web.dto.response.NewsResponse;
import com.sid.portal_web.entity.News.Member;
import com.sid.portal_web.entity.News.News;
import com.sid.portal_web.entity.News.NewsTopic;
import com.sid.portal_web.mapper.NewsMapper;
import com.sid.portal_web.repository.MemberRepository;
import com.sid.portal_web.repository.NewsRepository;
import com.sid.portal_web.repository.NewsTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;
    private final NewsTopicRepository newsTopicRepository;

    @Override
    public NewsResponse createNews(NewsRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        List<NewsTopic> tags = newsTopicRepository.findAllById(request.getTags());

        News news = NewsMapper.toEntity(request, member, tags);
        news.setPublishDate(LocalDateTime.now());

        News savedNews = newsRepository.save(news);

        return NewsMapper.toDto(savedNews);
    }

    @Override
    public List<NewsResponse> getAllNews() {
        return newsRepository.findAll()
                .stream()
                .map(NewsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsResponse> searchByTitle(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByTitle'");
    }
}