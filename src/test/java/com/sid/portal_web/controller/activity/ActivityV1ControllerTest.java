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


@WebMvcTest(ActivityV1Controller.class)
class ActivityV1ControllerTest {

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
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Creado con exito"));

    }

    @DisplayName("Debería actualizar completamente una actividad existente y retornar confirmación")
    @Test
    public void putActivities() throws Exception {
        String requestBody = """
        {
            "title": "Reunión actualizada",
            "description": "Reunión del comité con cambios",
            "priority": "MEDIA",
            "status": "ESPERA",
            "total_hours": 15,
            "start_date": "2025-06-01T10:00:00Z",
            "completation_date": "2025-06-15T17:00:00Z",
            "manager": "Ana Pérez Modificado",
            "members": [
                {
                    "name": "Carlos Ruiz Actualizado",
                    "identify": "CR123",
                    "isMember": false
                }
            ],
            "committees": [
                {
                    "committee_id": 2,
                    "description": "Nuevo comité de organización"
                }
            ]
        }
    """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/activities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Actividad actualizada con éxito"));
    }

    @DisplayName("Debería actualizar parcialmente una actividad con JSON Patch")
    @Test
    public void patchActivity() throws Exception {
        // Ejemplo de operación JSON Patch
        String patchRequest = """
        [
            {
                "op": "replace",
                "path": "/status",
                "value": "COMPLETADA"
            },
            {
                "op": "replace",
                "path": "/priority",
                "value": "BAJA"
            },
            {
                "op": "add",
                "path": "/members/-",
                "value": {
                    "name": "Nuevo Miembro",
                    "identify": "NM001",
                    "isMember": true
                }
            }
        ]
    """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/activities/1")
                        .contentType("application/json-patch+json") // Content-Type específico para JSON Patch
                        .header("Authorization", "Bearer dummy-token")
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Actividad actualizada parcialmente con éxito"));
    }

    @DisplayName("Debería fallar con operación PATCH inválida")
    @Test
    public void patchActivityWithInvalidOperation() throws Exception {
        String invalidPatch = """
        [
            {
                "op": "invalid",  // Operación no soportada
                "path": "/status",
                "value": "COMPLETADA"
            }
        ]
    """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/activities/1")
                        .contentType("application/json-patch+json")
                        .content(invalidPatch))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Debería fallar con ruta PATCH inválida")
    @Test
    public void patchActivityWithInvalidPath() throws Exception {
        String invalidPathPatch = """
        [
            {
                "op": "replace",
                "path": "/campo_inexistente",  // Campo que no existe
                "value": "valor"
            }
        ]
    """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/activities/1")
                        .contentType("application/json-patch+json")
                        .content(invalidPathPatch))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Debería eliminar una actividad existente")
    @Test
    public void deleteActivity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/activities/1")
                        .header("Authorization", "Bearer dummy-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Actividad eliminada con éxito"));
    }

}

