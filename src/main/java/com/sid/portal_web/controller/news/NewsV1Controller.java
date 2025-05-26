package com.sid.portal_web.controller.news;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar las noticias.
 * Versión 1 (para mejorar).
 */
@RestController
public class NewsV1Controller {

    /**
     * Endpoint para obtener una respuesta según un parámetro.
     * Ejemplo: /noticias?param=algo
     *
     * @param param Parámetro de consulta
     * @return una cadena con el contenido personalizado
     */
    @GetMapping("/noticias")
    public String getNews(@RequestParam String param) {
        return "Parámetro recibido: " + param;
    }

    /**
     * Endpoint informativo.
     *
     * @return texto fijo "noticias"
     */
    @GetMapping("/noticias/info")
    public String info() {
        return "noticias";
    }
}
