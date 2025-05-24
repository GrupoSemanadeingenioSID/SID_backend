package com.sid.portal_web.controller.team;

import com.sid.portal_web.auth.jwt.JwtAuthenticationFilter;
import com.sid.portal_web.auth.service.interfaces.JwtService;
import com.sid.portal_web.dto.response.TeamBaseResponse;
import com.sid.portal_web.service.team.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = TeamV1Controller.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        }
)
@Import({JacksonAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
class TeamV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeamService teamService;


    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter authenticationFilter;

    @Test
    void getTeams_shouldReturnPageOfTeams() throws Exception {
        // 1. Crear datos REALES que coincidan con tu endpoint
        List<TeamBaseResponse> teams = List.of(
                new TeamBaseResponse(1, "Equipo Frontend", "Equipo especializado en desarrollo frontend", true, "Juan Carlos"),
                new TeamBaseResponse(2, "Equipo Backend", "Equipo especializado en desarrollo backend", true, "Carlos Alberto"),
                new TeamBaseResponse(3, "Equipo Mobile", "Equipo de desarrollo móvil", true, "Juan Carlos")
        );

        // 2. Crear el Page EXACTO como lo devuelve tu endpoint
        Page<TeamBaseResponse> mockPage = new PageImpl<>(
                teams,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "formation_date")),
                3L
        );

        // 3. Mockear el servicio PARA DEVOLVER LO QUE ESPERA TU CONTROLADOR REAL
        when(teamService.findAll(any(Pageable.class))).thenReturn(mockPage);

        // 4. Ejecutar el test con los JSON Paths CORRECTOS (basado en tu JSON real)
        mockMvc.perform(get("/api/v1/teams")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "formation_date,desc")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) // ¡IMPRIME LA RESPUESTA PARA DEBUG!
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Equipo Frontend"))
                .andExpect(jsonPath("$.content[1].description").value("Equipo especializado en desarrollo backend"))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0));
    }
    @Test
    void getTeams_shouldReturnEmptyPageWhenNoTeams() throws Exception {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "formation_date"));
        Page<TeamBaseResponse> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(teamService.findAll(any(Pageable.class))).thenReturn(emptyPage);

        // Act & Assert
        mockMvc.perform(get("/api/v1/teams")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "formation_date,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0)) // Or 0 depending on PageImpl behavior for empty lists with non-zero total
                .andExpect(jsonPath("$.pageable.pageNumber").value(0));
    }
}