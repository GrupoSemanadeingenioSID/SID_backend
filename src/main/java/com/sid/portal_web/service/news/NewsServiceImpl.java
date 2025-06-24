package com.sid.portal_web.service.news;

import com.sid.portal_web.core.NewsCore;
import com.sid.portal_web.dto.request.NewsRequest;
import com.sid.portal_web.dto.response.NewsResponse;
import com.sid.portal_web.entity.News.Member;
import com.sid.portal_web.entity.News.News;
import com.sid.portal_web.entity.News.NewsTopic;
import com.sid.portal_web.mapper.NewsMapper;
import com.sid.portal_web.repository.MemberRepository;
import com.sid.portal_web.repository.NewsRepository;
import com.sid.portal_web.repository.NewsTopicRepository;
import com.sid.portal_web.service.news.NewsService;
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
        // Validaciones de reglas de negocio
        NewsCore.validateCreateRequest(request);

        // Buscar autor
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        // Validar autor activo
        NewsCore.validateAuthor(member);

        // Buscar etiquetas
        List<NewsTopic> tags = newsTopicRepository.findAllById(request.getTags());

        // Mapear a entidad
        News news = NewsMapper.toEntity(request, member, tags);
        news.setPublishDate(LocalDateTime.now());

        // Guardar en base de datos
        News savedNews = newsRepository.save(news);

        // Mapear de vuelta a DTO
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
        return newsRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(NewsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsResponse getNewsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNewsById'");
    }

    @Override
    public NewsResponse updateNews(Long id, NewsRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateNews'");
    }

    @Override
    public void deleteNews(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteNews'");
    }
}
