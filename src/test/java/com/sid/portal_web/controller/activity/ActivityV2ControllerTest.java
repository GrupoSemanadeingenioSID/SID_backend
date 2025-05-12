package com.sid.portal_web.controller.activity;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@WebMvcTest(ActivityV2Controller.class)
class ActivityV2ControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Debería retornar todas las actividades paginadas correctamente")
    @Test
    public void getActivities() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/activities?page=0&size=10"))
                .andExpect(status().isOk()) // los expect esperan algo
                .andExpect(jsonPath("$.content").isArray()) // aqui espera que haya un apartado de content que sea lista
                .andExpect(jsonPath("$.pageable").exists()) // este un pageable
                .andExpect(jsonPath("$.totalElements").isNumber()) // este un total de elementos, esto es minimo.
                // Verificar estructura de los elementos en content
                .andExpect(jsonPath("$.content[0].id").isNumber())
                .andExpect(jsonPath("$.content[0].title").isString())
                .andExpect(jsonPath("$.content[0].description").isString())
                .andExpect(jsonPath("$.content[0].priority").value(is(in(Arrays.asList("ALTA", "MEDIA", "BAJA")))))
                .andExpect(jsonPath("$.content[0].status").value(is(in(Arrays.asList("ACTIVA", "ESPERA", "COMPLETADA", "CANCELADA")))))
                .andExpect(jsonPath("$.content[0].total_hours").isNumber())
                .andExpect(jsonPath("$.content[0].start_date").isString())
                .andExpect(jsonPath("$.content[0].completation_date").isString())
                .andExpect(jsonPath("$.content[0].manager").isString())
                .andExpect(jsonPath("$.content[0].created_at").isString());
    }

    @DisplayName("Debe retornar un solo elemento especifico")
    @Test
    public void getActivityById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.priority").value(is(in(Arrays.asList("ALTA", "MEDIA", "BAJA")))))
                .andExpect(jsonPath("$.status").value(is(in(Arrays.asList("ACTIVA", "ESPERA", "COMPLETADA", "CANCELADA")))))
                .andExpect(jsonPath("$.total_hours").isNumber())
                .andExpect(jsonPath("$.start_date").isString())
                .andExpect(jsonPath("$.completation_date").isString())
                .andExpect(jsonPath("$.manager").isString())
                // Miembros
                .andExpect(jsonPath("$.members").isArray())
                .andExpect(jsonPath("$.members[0].name").isString())
                .andExpect(jsonPath("$.members[0].identify").isString())
                .andExpect(jsonPath("$.members[0].isMember").isBoolean())
                // Comites
                .andExpect(jsonPath("$.committees").isArray())
                .andExpect(jsonPath("$.committees[0].name").isString())
                .andExpect(jsonPath("$.committees[0].leader").isString())
                .andExpect(jsonPath("$.committees[0].description").isString());
    }
    @DisplayName("Debería retornar la creacion de un elemento")
    @Test
    public void postActivities() throws Exception {
        String requestBody =
                """
                                 {
                                         "title": "Reunión inicial",
                                         "description": "Primera reunión del comité",
                                         "priority": "ALTA",
                                         "status": "ACTIVA",
                                         "total_hours": 10,
                                         "start_date": "2025-05-15T10:00:00Z",
                                         "completation_date": "2025-05-30T17:00:00Z",
                                         "manager": "Ana Pérez",
                                         "members": [
                                             {
                                                 "name": "Carlos Ruiz",
                                                 "identify": "CR123",
                                                 "isMember": true
                                             }
                                         ],
                                         "committees": [
                                             {
                                                 "committee_id": 1,
                                                 "description": "Organización logística"
                                             }
                                         ]
                                     }       \s
                        \s""";

        String responseBody = """
                {
                    "id": 1,
                    "title": "Nueva actividad",
                    "status": "ACTIVA",
                    "created_at": "2025-05-12T08:00:00Z"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Nueva actividad"))
                .andExpect(jsonPath("$.status").value("ACTIVA"))
                .andExpect(jsonPath("$.created_at").value("2025-05-12T08:00:00Z"));
    }


}

