package com.sid.portal_web.controller.project;

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


@WebMvcTest(ProjectV1Controller.class)
class ProjectV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Debería retornar todos los proyectos paginados correctamente")
    @Test
    public void getProjects() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/projects?page=0&size=10&sort=startDate,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.totalElements").isNumber())
                // Verificar estructura de los elementos en content
                .andExpect(jsonPath("$.content[0].id").isNumber())
                .andExpect(jsonPath("$.content[0].name").isString())
                .andExpect(jsonPath("$.content[0].description").isString())
                .andExpect(jsonPath("$.content[0].start_date").isString())
                .andExpect(jsonPath("$.content[0].end_date").isString())
                .andExpect(jsonPath("$.content[0].budget").isNumber())
                .andExpect(jsonPath("$.content[0].development_id").isString())
                .andExpect(jsonPath("$.content[0].status").value(is(in(Arrays.asList("ACTIVO", "COMPLETADO", "EN_PAUSA", "CANCELADO")))));
    }

    @DisplayName("Debe retornar un proyecto específico con todos sus detalles")
    @Test
    public void getProjectById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.start_date").isString())
                .andExpect(jsonPath("$.end_date").isString())
                .andExpect(jsonPath("$.budget").isNumber())
                .andExpect(jsonPath("$.development_id").isString())
                .andExpect(jsonPath("$.status").value(is(in(Arrays.asList("ACTIVO", "COMPLETADO", "EN_PAUSA", "CANCELADO")))))
                // Objetivos
                .andExpect(jsonPath("$.goals").isArray())
                .andExpect(jsonPath("$.goals[0].title").isString())
                .andExpect(jsonPath("$.goals[0].description").isString())
                .andExpect(jsonPath("$.goals[0].completion_date").isString())
                .andExpect(jsonPath("$.goals[0].status").isString())
                // Equipos
                .andExpect(jsonPath("$.teams").isArray())
                .andExpect(jsonPath("$.teams[0].id").isNumber())
                .andExpect(jsonPath("$.teams[0].name").isString())
                .andExpect(jsonPath("$.teams[0].description").isString())
                .andExpect(jsonPath("$.teams[0].formation_date").isString())
                .andExpect(jsonPath("$.teams[0].active").isBoolean())
                .andExpect(jsonPath("$.teams[0].leader").isString())
                // Miembros de equipos
                .andExpect(jsonPath("$.teams[0].members").isArray())
                .andExpect(jsonPath("$.teams[0].members[0].development_member_id").isNumber())
                .andExpect(jsonPath("$.teams[0].members[0].name").isString())
                .andExpect(jsonPath("$.teams[0].members[0].join_date").isString())
                .andExpect(jsonPath("$.teams[0].members[0].end_date").isString())
                .andExpect(jsonPath("$.teams[0].members[0].rol").isString())
                .andExpect(jsonPath("$.teams[0].members[0].title").isString());
    }

    @DisplayName("Debería crear un nuevo proyecto y retornar confirmación")
    @Test
    public void postProject() throws Exception {
        String requestBody = """
        {
            "name": "Proyecto de Desarrollo",
            "description": "Proyecto para desarrollar nueva plataforma",
            "start_date": "2025-06-01T00:00:00Z",
            "end_date": "2025-12-31T00:00:00Z",
            "budget": 50000,
            "development_id": "2",
            "status": "ACTIVO",
            "goals": [
                {
                    "title": "Primer hito",
                    "description": "Completar diseño inicial",
                    "completion_date": "2025-07-15T00:00:00Z",
                    "status": "PENDIENTE"
                }
            ],
            "teams": [
                {
                    "name": "Equipo de desarrollo",
                    "description": "Equipo principal de desarrollo",
                    "formation_date": "2025-05-01T00:00:00Z",
                    "active": true,
                    "leader": "Juan Pérez",
                    "members": [
                        {
                            "development_member_id": 101,
                            "join_date": "2025-05-01T00:00:00Z",
                            "rol": "Desarrollador",
                            "title": "Ingeniero de Software"
                        }
                    ]
                }
            ]
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Proyecto creado con éxito"));
    }

    @DisplayName("Debería actualizar completamente un proyecto existente")
    @Test
    public void putProject() throws Exception {
        String requestBody = """
        {
            "name": "Proyecto Actualizado",
            "description": "Descripción actualizada del proyecto",
            "start_date": "2025-06-01T00:00:00Z",
            "end_date": "2025-12-31T00:00:00Z",
            "budget": 55000,
            "development_id": "2",
            "status": "EN_PAUSA",
            "goals": [
                {
                    "title": "Primer hito actualizado",
                    "description": "Completar diseño inicial revisado",
                    "completion_date": "2025-08-15T00:00:00Z",
                    "status": "EN_PROGRESO"
                }
            ],
            "teams": [
                {
                    "name": "Equipo de desarrollo actualizado",
                    "description": "Equipo principal de desarrollo mejorado",
                    "formation_date": "2025-05-01T00:00:00Z",
                    "active": true,
                    "leader": "Juan Pérez Modificado",
                    "members": [
                        {
                            "development_member_id": 101,
                            "join_date": "2025-05-01T00:00:00Z",
                            "end_date": null,
                            "rol": "Desarrollador Senior",
                            "title": "Ingeniero de Software Principal"
                        }
                    ]
                }
            ]
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Proyecto actualizado con éxito"));
    }

    @DisplayName("Debería actualizar parcialmente un proyecto con JSON Patch")
    @Test
    public void patchProject() throws Exception {
        String patchRequest = """
        [
            {
                "op": "replace",
                "path": "/status",
                "value": "COMPLETADO"
            },
            {
                "op": "replace",
                "path": "/budget",
                "value": 60000
            },
            {
                "op": "add",
                "path": "/goals/-",
                "value": {
                    "title": "Nuevo hito agregado",
                    "description": "Documentación final del proyecto",
                    "completion_date": "2025-12-15T00:00:00Z",
                    "status": "PENDIENTE"
                }
            }
        ]
        """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/projects/1")
                        .contentType("application/json-patch+json")
                        .header("Authorization", "Bearer dummy-token")
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Proyecto actualizado parcialmente con éxito"));
    }

    @DisplayName("Debería fallar con operación PATCH inválida en proyecto")
    @Test
    public void patchProjectWithInvalidOperation() throws Exception {
        String invalidPatch = """
        [
            {
                "op": "invalid",  // Operación no soportada
                "path": "/status",
                "value": "COMPLETADO"
            }
        ]
        """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/projects/1")
                        .contentType("application/json-patch+json")
                        .content(invalidPatch))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Debería eliminar un proyecto existente")
    @Test
    public void deleteProject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/projects/1")
                        .header("Authorization", "Bearer dummy-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Proyecto eliminado con éxito"));
    }
}