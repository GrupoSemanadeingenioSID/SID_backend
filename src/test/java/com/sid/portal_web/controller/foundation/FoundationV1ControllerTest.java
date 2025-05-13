package com.sid.portal_web.controller.foundation;

import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(FoundationV1Controller.class)
class FoundationV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Debería retornar todos los aliados y fundaciones correctamente")
    @Test
    void getAllPartners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/partners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray()) // aqui espera que haya un apartado de content que sea lista
                .andExpect(jsonPath("$.pageable").exists()) // este un pageable
                .andExpect(jsonPath("$.totalElements").isNumber()) // este un total de elementos, esto es minimo.
                // Verificar estructura de los elementos en content

                .andExpect(jsonPath("$.content[0].id").isNumber())
                .andExpect(jsonPath("$.content[0].name").isString())
                .andExpect(jsonPath("$.content[0].description").isString())
                .andExpect(jsonPath("$.content[0].type").value(is(in(new String[]{"ALIADO", "FUNDACION"}))))
                .andExpect(jsonPath("$.content[0].logo_url").isString());
    }

    @DisplayName("Debería retornar un aliado o fundación por su ID")
    @Test
    void getPartnerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/partners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.type").value(is(in(new String[]{"ALIADO", "FUNDACION"}))))
                .andExpect(jsonPath("$.logo_url").isString());
    }

    @DisplayName("Debería crear un nuevo aliado o fundación")
    @Test
    void postPartner() throws Exception {
        String requestBody = """
        {
            "name": "Fundación Esperanza",
            "description": "Apoyo a comunidades rurales",
            "type": "FUNDACION",
            "logo_url": "https://example.com/logo.png"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Creado con exito"));
    }

    @DisplayName("Debería actualizar completamente un aliado o fundación existente")
    @Test
    void putPartner() throws Exception {
        String requestBody = """
        {
            "name": "Fundación Actualizada",
            "description": "Descripción actualizada",
            "type": "ALIADO",
            "logo_url": "https://example.com/nuevo_logo.png"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/partners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Aliado o fundación actualizada con éxito"));
    }

    @DisplayName("Debería actualizar parcialmente un aliado o fundación existente")
    @Test
    void patchPartner() throws Exception {
        String requestBody = """
        {
            "name": "Nombre Parcialmente Actualizado"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/partners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Aliado o fundación actualizada con éxito"));
    }

    @DisplayName("Debería eliminar un aliado o fundación existente")
    @Test
    void deletePartner() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/partners/1")
                        .header("Authorization", "Bearer dummy-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Aliado o fundación eliminado con éxito"));
    }
}