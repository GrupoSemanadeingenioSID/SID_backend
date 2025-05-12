package com.sid.portal_web.controller.foundation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoundationV1Controller.class)
class FoundationV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFoundations() throws Exception {

        //perfom simula una peticion HTTP, en este caso un GET
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations?page=0&size=10"))
                .andExpect(status().isOk()) // los expect esperan algo
                .andExpect(jsonPath("$.content").isArray()) // aqui espera que haya un apartado de content que sea lista
                .andExpect(jsonPath("$.pageable").exists()) // este un pageable
                .andExpect(jsonPath("$.totalElements").isNumber()); // este un total de elementos, esto es minimo.
    }
}