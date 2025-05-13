package com.sid.portal_web.controller.news;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



@WebMvcTest(NewsV1Controller.class)
class NewsV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Debería retornar todas las noticias paginadas correctamente")
    @Test
    public void getAllNews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news?page=0&size=10&sort=publishDate,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.totalElements").isNumber())
                // Verificar estructura básica de los elementos en content
                .andExpect(jsonPath("$.content[0].id").isNumber())
                .andExpect(jsonPath("$.content[0].title").isString())
                .andExpect(jsonPath("$.content[0].description").isString())
                .andExpect(jsonPath("$.content[0].publishDate").isString())
                .andExpect(jsonPath("$.content[0].author").isString())
                .andExpect(jsonPath("$.content[0].imageUrl").isString())
                .andExpect(jsonPath("$.content[0].tags").isArray());
    }

    @DisplayName("Debe retornar una noticia específica con todos sus detalles")
    @Test
    public void getNewsById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.content").isString())
                .andExpect(jsonPath("$.publishDate").isString())
                .andExpect(jsonPath("$.author").isString())
                .andExpect(jsonPath("$.imageUrl").isString())
                .andExpect(jsonPath("$.tags").isArray());
    }

    @DisplayName("Debería crear una nueva noticia y retornar confirmación")
    @Test
    public void createNews() throws Exception {
        String requestBody = """
        {
            "title": "Nueva actualización del sistema",
            "description": "Descripción breve de la actualización",
            "content": "<p>Contenido completo en HTML de la noticia</p>",
            "tags": ["tecnología", "actualización"],
            "imageUrl": "https://example.com/image.jpg"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Noticia creada con éxito"));
    }

    @DisplayName("Debería actualizar completamente una noticia existente")
    @Test
    public void updateNews() throws Exception {
        String requestBody = """
        {
            "title": "Actualización del sistema - Versión 2.0",
            "content": "<p>Nuevo contenido actualizado</p>",
            "imageUrl": "https://example.com/new-image.jpg"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/news/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Noticia actualizada con éxito"));
    }

    @DisplayName("Debería actualizar parcialmente una noticia con JSON Patch")
    @Test
    public void patchNews() throws Exception {
        String patchRequest = """
        [
            {
                "op": "replace",
                "path": "/title",
                "value": "Título actualizado"
            },
            {
                "op": "replace",
                "path": "/imageUrl",
                "value": "https://example.com/updated-image.jpg"
            },
            {
                "op": "add",
                "path": "/tags/-",
                "value": "nueva-etiqueta"
            }
        ]
        """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/news/1")
                        .contentType("application/json-patch+json")
                        .header("Authorization", "Bearer dummy-token")
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Noticia actualizada parcialmente con éxito"));
    }

    @DisplayName("Debería fallar al intentar actualizar con operación PATCH inválida")
    @Test
    public void patchNewsWithInvalidOperation() throws Exception {
        String invalidPatch = """
        [
            {
                "op": "invalid",
                "path": "/title",
                "value": "Título inválido"
            }
        ]
        """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/news/1")
                        .contentType("application/json-patch+json")
                        .content(invalidPatch))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Debería eliminar una noticia existente")
    @Test
    public void deleteNews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/news/1")
                        .header("Authorization", "Bearer dummy-token"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("Debería fallar al crear noticia sin título")
    @Test
    public void createNewsWithoutTitleShouldFail() throws Exception {
        String invalidRequestBody = """
        {
            "description": "Descripción sin título",
            "content": "Contenido sin título"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }
}
