package com.sid.portal_web.core;

import com.sid.portal_web.dto.request.NewsRequest;
import com.sid.portal_web.entity.News.Member;

public class NewsCore {

    public static void validateCreateRequest(NewsRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException(" El contenido no puede estar vacío");
        }

        if (request.getImageUrl() == null || request.getImageUrl().isBlank()) {
            throw new IllegalArgumentException("La URL de la imagen es obligatoria");
        }

        if (request.getTags() == null || request.getTags().isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos una etiqueta");
        }
    }

    public static void validateAuthor(Member member) {
        if (member == null || member.getUser() == null) {
            throw new IllegalStateException("El autor no existe");
        }

        if (!member.getUser().isActive()) {
            throw new IllegalStateException("El autor no está activo");
        }
    }
}
