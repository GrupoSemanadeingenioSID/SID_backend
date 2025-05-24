package com.sid.portal_web.controller.team;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.sid.portal_web.auth.jwt.JwtAuthenticationFilter;
import com.sid.portal_web.auth.service.interfaces.JwtService;
import com.sid.portal_web.dto.response.TeamBaseResponse;
import com.sid.portal_web.service.team.TeamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;


@WebMvcTest(TeamV1Controller.class)
class TeamV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean  // <
    private TeamService teamService;

//    @MockitoBean
//    private JwtService jwtService;  // MOCKEAR el servicio que falta
//
//    @MockitoBean
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//

    @DisplayName("Debería retornar todos los equipos paginados correctamente")
    @Test
    public void getTeams() throws Exception {
        // Mock del resultado del servicio
        List<TeamBaseResponse> teams = Arrays.asList(
                TeamBaseResponse.builder()
                        .id(1)
                        .name("Team 1")
                        .description("Description 1")
                        .active(true)
                        .leader("Leader 1")
                        .build()
        );

        Page<TeamBaseResponse> page = new PageImpl<>(teams);

        // Configurar el comportamiento del mock
        when(teamService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teams?page=0&size=10&sort=formation_date,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.totalElements").isNumber())
                .andExpect(jsonPath("$.content[0].id").isNumber())
                .andExpect(jsonPath("$.content[0].name").isString())
                .andExpect(jsonPath("$.content[0].description").isString())
                .andExpect(jsonPath("$.content[0].active").isBoolean())
                .andExpect(jsonPath("$.content[0].leader").isString());
    }


    @DisplayName("Debe retornar un equipo específico con sus miembros")
    @Test
    public void getTeamById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.active").isBoolean())
                .andExpect(jsonPath("$.leader").isString())
                .andExpect(jsonPath("$.members").isArray())
                .andExpect(jsonPath("$.members[0].name").isString())
                .andExpect(jsonPath("$.members[0].rol").isString())
                .andExpect(jsonPath("$.members[0].title").isString());
    }

    @DisplayName("Debería crear un nuevo equipo con sus miembros")
    @Test
    public void postTeam() throws Exception {
        String requestBody = """
        {
            "name": "Equipo Backend",
            "description": "Encargado del desarrollo del backend",
            "formation_date": "2025-05-01T00:00:00Z",
            "active": true,
            "leader": "Ana Gómez",
            "members": [
                {
                    "development_member_id": 201,
                    "join_date": "2025-05-01T00:00:00Z",
                    "end_date": "2025-08-01T00:00:00Z",
                    "rol": "Backend Developer",
                    "title": "Ingeniera de Software"
                }
            ]
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Equipo creado con éxito"));
    }

    @DisplayName("Debería actualizar un equipo existente")
    @Test
    public void putTeam() throws Exception {
        String requestBody = """
        {
            "name": "Equipo Backend Actualizado",
            "description": "Equipo actualizado con nuevos miembros",
            "formation_date": "2025-05-01T00:00:00Z",
            "active": true,
            "leader": "Ana Gómez Actualizada",
            "members": [
                {
                    "development_member_id": 201,
                    "join_date": "2025-05-01T00:00:00Z",
                    "end_date": null,
                    "rol": "Backend Senior",
                    "title": "Ingeniera Principal"
                }
            ]
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/teams/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer dummy-token")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Equipo actualizado con éxito"));
    }

    @DisplayName("Debería actualizar parcialmente un equipo usando JSON Patch")
    @Test
    public void patchTeam() throws Exception {
        String patchRequest = """
    [
        { "op": "replace", "path": "/name", "value": "Equipo de IA" },
        { "op": "replace", "path": "/active", "value": false },
        { "op": "replace", "path": "/leader", "value": "Luis Rojas" }
    ]
    """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/teams/1")
                        .contentType("application/json-patch+json")
                        .header("Authorization", "Bearer dummy-token")
                        .content(patchRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Equipo actualizado parcialmente con éxito"));
    }


    @DisplayName("Debería eliminar un equipo por su ID")
    @Test
    public void deleteTeam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/teams/1")
                        .header("Authorization", "Bearer dummy-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Equipo eliminado con éxito"));
    }
}
