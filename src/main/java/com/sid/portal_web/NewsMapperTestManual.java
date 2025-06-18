package com.sid.portal_web;

import com.sid.portal_web.dto.request.NewsRequest;
import com.sid.portal_web.dto.response.NewsResponse;
import com.sid.portal_web.entity.*;
import com.sid.portal_web.entity.News.News;
import com.sid.portal_web.entity.News.NewsTopic;
import com.sid.portal_web.entity.News.Member;
import com.sid.portal_web.mapper.NewsMapper;

import java.time.LocalDateTime;
import java.util.List;

public class NewsMapperTestManual {
    public static void main(String[] args) {

        // 1. Crear usuario (quien escribió la noticia)
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setEmail("autor@correo.com");
        user.setActive(true);

        // 2. Crear miembro (autor vinculado a user)
        Member member = new Member();
        member.setId(1L);
        member.setUser(user);

        // 3. Crear tag/categoría
        NewsTopic tag = new NewsTopic();
        tag.setId("tech");
        tag.setName("Tecnología");
        tag.setDescription("Noticias tecnológicas");

        // 4. Crear NewsRequest (como si viniera de Postman)
        NewsRequest request = NewsRequest.builder()
                .title("¡Primera noticia!")
                .description("Resumen resumen resumen")
                .content("Esto es el contenido largo")
                .imageUrl("https://foto.com/img.jpg")
                .memberId(1L)
                .tags(List.of("tech"))
                .build();

        // 5. Usar el mapper para convertir el DTO en entidad
        News news = NewsMapper.toEntity(request, member, List.of(tag));
        news.setId(99L); // simular que fue guardado
        news.setPublishDate(LocalDateTime.now());

        // 6. Usar el mapper para convertir la entidad en DTO de respuesta
        NewsResponse response = NewsMapper.toDto(news);

        // 7. Mostrar todo por consola
        System.out.println("🧱 ENTIDAD News:");
        System.out.println(news);
        System.out.println("\n🎁 DTO NewsResponse:");
        System.out.println(response);
    }
}
