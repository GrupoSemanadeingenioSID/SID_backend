package com.sid.portal_web.controller.foundation;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sid.portal_web.dto.response.FoundationContactResponse;
import com.sid.portal_web.dto.response.FoundationDashboardResponse;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.dto.response.SocialMediaResponse;
import com.sid.portal_web.service.foundation.FoundationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebMvcTest(FoundationV1Controller.class)
class FoundationV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoundationService foundationService;


    // ========== TESTS PARA GET ALL (/api/v1/foundations) ==========

    @DisplayName("Debería retornar todas las fundaciones con parámetros por defecto")
    @Test
    void getAllPartners_DefaultParameters() throws Exception {
        // Arrange
        Page<FoundationResponse> mockPage = createMockPage(2, 0, 10);
        when(foundationService.findAll(eq(0), eq(10), eq("id"), eq(true))).thenReturn(mockPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.pageable.pageNumber", is(0)))
                .andExpect(jsonPath("$.pageable.pageSize", is(10)))
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Fundación Test 1")))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[1].name", is("Fundación Test 2")));

        verify(foundationService, times(1)).findAll(eq(0), eq(10), eq("id"), eq(true));
    }

    @DisplayName("Debería retornar fundaciones ordenadas por ascendente/descendente")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void getAllPartners_WithSorting(boolean ascending) throws Exception {
        // Arrange
        Page<FoundationResponse> mockPage = createMockPage(2, 0, 10);
        when(foundationService.findAll(eq(0), eq(10), eq("id"), eq(ascending))).thenReturn(mockPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations")
                        .param("asc", String.valueOf(ascending)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));

        verify(foundationService, times(1)).findAll(eq(0), eq(10), eq("id"), eq(ascending));
    }

    @DisplayName("Debería retornar fundaciones ordenadas por campo específico")
    @Test
    void getAllPartners_WithSortByField() throws Exception {
        // Arrange
        Page<FoundationResponse> mockPage = createMockPage(2, 0, 10);
        when(foundationService.findAll(eq(0), eq(10), eq("name"), eq(true))).thenReturn(mockPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations")
                        .param("sortBy", "name")
                        .param("asc", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));

        verify(foundationService, times(1)).findAll(eq(0), eq(10), eq("name"), eq(true));
    }

    @DisplayName("Debería retornar página vacía cuando no hay resultados")
    @Test
    void getAllPartners_EmptyResult() throws Exception {
        // Arrange
        Page<FoundationResponse> emptyPage = new PageImpl<>(
                Collections.emptyList(),
                PageRequest.of(0, 10),
                0
        );
        when(foundationService.findAll(eq(0), eq(10), eq("id"), eq(true))).thenReturn(emptyPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.empty", is(true)));

        verify(foundationService, times(1)).findAll(eq(0), eq(10), eq("id"), eq(true));
    }

    // ========== TESTS PARA GET BY ID (/api/v1/foundations/{id}) ==========

    @DisplayName("Debería retornar fundación por ID válido")
    @Test
    void getPartnerById_ValidId() throws Exception {
        // Arrange
        FoundationContactResponse foundation = createFoundationContactResponse(1);
        when(foundationService.findById(1)).thenReturn(Optional.of(foundation));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Fundación Test")))
                .andExpect(jsonPath("$.description", is("Descripción test")))
                .andExpect(jsonPath("$.logo_url", is("https://example.com/logo.png")))
                .andExpect(jsonPath("$.phone", is("123456789")))
                .andExpect(jsonPath("$.address", is("Calle Principal 123")))
                .andExpect(jsonPath("$.website", is("https://fundaciontest.org")))
                .andExpect(jsonPath("$.location", is("Ciudad Ejemplo")))
                .andExpect(jsonPath("$.socialMedia", hasSize(2)))
                .andExpect(jsonPath("$.socialMedia[0].name", is("Facebook")))
                .andExpect(jsonPath("$.socialMedia[1].name", is("Twitter")));

        verify(foundationService, times(1)).findById(1);
    }

    @DisplayName("Debería retornar 404 para ID no existente")
    @ParameterizedTest
    @ValueSource(ints = {999, 0, -1, 100})
    void getPartnerById_NotFound(int id) throws Exception {
        // Arrange
        when(foundationService.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations/" + id))
                .andExpect(status().isNotFound());

        verify(foundationService, times(1)).findById(id);
    }

    @DisplayName("Debería retornar fundación sin redes sociales")
    @Test
    void getPartnerById_WithoutSocialMedia() throws Exception {
        // Arrange
        FoundationContactResponse foundation = FoundationContactResponse.builder()
                .id(1)
                .name("Fundación Sin Redes")
                .description("Descripción")
                .logo_url("https://example.com/logo.png")
                .phone("123456789")
                .address("Calle Principal 123")
                .website("https://fundacion.org")
                .location("Ciudad")
                .socialMedia(Collections.emptyList())
                .build();

        when(foundationService.findById(1)).thenReturn(Optional.of(foundation));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socialMedia", hasSize(0)))
                .andExpect(jsonPath("$.socialMedia", is(empty())));

        verify(foundationService, times(1)).findById(1);
    }

    @DisplayName("Debería manejar campos nulos en la respuesta")
    @Test
    void getPartnerById_WithNullFields() throws Exception {
        // Arrange
        FoundationContactResponse foundation = FoundationContactResponse.builder()
                .id(1)
                .name("Fundación")
                .description(null) // Campo nulo
                .logo_url(null)    // Campo nulo
                .phone("123456789")
                .address("Dirección")
                .website(null)     // Campo nulo
                .location("Ciudad")
                .socialMedia(Collections.emptyList())
                .build();

        when(foundationService.findById(1)).thenReturn(Optional.of(foundation));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Fundación")))
                .andExpect(jsonPath("$.description").doesNotExist())
                .andExpect(jsonPath("$.logo_url").doesNotExist())
                .andExpect(jsonPath("$.website").doesNotExist())
                .andExpect(jsonPath("$.phone", is("123456789")))
                .andExpect(jsonPath("$.address", is("Dirección")));

        verify(foundationService, times(1)).findById(1);
    }

    // ========== TESTS PARA GET BY ID (/api/v1/foundations/{id}/dashboard) ==========
    @DisplayName("Debería retornar fundación dashboard por ID válido")
    @Test
    void getFoundationDashboardById_ValidId() throws Exception {
        // Arrange
        FoundationDashboardResponse foundation = createFoundationDashboardResponse(1);
        when(foundationService.findByIdDashboard(1)).thenReturn(Optional.of(foundation));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations/1/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Fundación Dashboard")))
                .andExpect(jsonPath("$.description", is("Descripción del dashboard")))
                .andExpect(jsonPath("$.logo_url", is("https://example.com/logo.png")))
                .andExpect(jsonPath("$.phone", is("123456789")))
                .andExpect(jsonPath("$.address", is("Calle Dashboard 321")))
                .andExpect(jsonPath("$.website", is("https://fundaciondashboard.org")))
                .andExpect(jsonPath("$.location", is("Ciudad Dashboard")))
                .andExpect(jsonPath("$.quantity_proyects", is(5)))
                .andExpect(jsonPath("$.socialMedia", hasSize(2)))
                .andExpect(jsonPath("$.socialMedia[0].name", is("Facebook")))
                .andExpect(jsonPath("$.socialMedia[1].name", is("Twitter")));

        verify(foundationService, times(1)).findByIdDashboard(1);
    }

    @DisplayName("Debería retornar 404 para ID de dashboard no existente")
    @ParameterizedTest
    @ValueSource(ints = {999, 0, -1, 123456})
    void getFoundationDashboardById_NotFound(int id) throws Exception {
        // Arrange
        when(foundationService.findByIdDashboard(id)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foundations/" + id + "/dashboard"))
                .andExpect(status().isNotFound());

        verify(foundationService, times(1)).findByIdDashboard(id);
    }


    // ========== MÉTODOS AUXILIARES ==========

    private Page<FoundationResponse> createMockPage(int contentSize, int pageNumber, int pageSize) {
        List<FoundationResponse> foundations = Arrays.asList(
                FoundationResponse.builder()
                        .id(1)
                        .name("Fundación Test 1")
                        .description("Descripción test 1")
                        .logo_url("https://example.com/logo1.png")
                        .build(),
                FoundationResponse.builder()
                        .id(2)
                        .name("Fundación Test 2")
                        .description("Descripción test 2")
                        .logo_url("https://example.com/logo2.png")
                        .build()
        ).subList(0, Math.min(contentSize, 2));

        return new PageImpl<>(
                foundations,
                PageRequest.of(pageNumber, pageSize),
                contentSize
        );
    }

    private FoundationContactResponse createFoundationContactResponse(int id) {
        return FoundationContactResponse.builder()
                .id(id)
                .name("Fundación Test")
                .description("Descripción test")
                .logo_url("https://example.com/logo.png")
                .phone("123456789")
                .address("Calle Principal 123")
                .website("https://fundaciontest.org")
                .location("Ciudad Ejemplo")
                .socialMedia(List.of(
                        SocialMediaResponse.builder()
                                .id("1")
                                .name("Facebook")
                                .url("https://facebook.com")
                                .account("fundacion_fb")
                                .build(),
                        SocialMediaResponse.builder()
                                .id("2")
                                .name("Twitter")
                                .url("https://twitter.com")
                                .account("fundacion_tw")
                                .build()
                ))
                .build();
    }


    private FoundationDashboardResponse createFoundationDashboardResponse(int id) {
        return FoundationDashboardResponse.builder()
                .id(id)
                .name("Fundación Dashboard")
                .description("Descripción del dashboard")
                .logo_url("https://example.com/logo.png")
                .phone("123456789")
                .address("Calle Dashboard 321")
                .website("https://fundaciondashboard.org")
                .location("Ciudad Dashboard")
                .socialMedia(List.of(
                        SocialMediaResponse.builder()
                                .id("1")
                                .name("Facebook")
                                .url("https://facebook.com")
                                .account("fundacion_fb")
                                .build(),
                        SocialMediaResponse.builder()
                                .id("2")
                                .name("Twitter")
                                .url("https://twitter.com")
                                .account("fundacion_tw")
                                .build()
                ))
                .quantity_proyects(5)
                .build();
    }


    @DisplayName("Debería crear un nuevo aliado o fundación")
    @Test
    void postPartner() throws Exception {
        String requestBody = """
        {
            "name": "Fundación Esperanza",
            "description": "Apoyo a comunidades rurales",
            "type": "FUNDACION",
            "logoUrl": "https://example.com/logo.png"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/foundations")
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
            "logoUrl": "https://example.com/nuevo_logo.png"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/foundations/1")
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

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/foundations/1")
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/foundations/1")
                        .header("Authorization", "Bearer dummy-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Aliado o fundación eliminado con éxito"));
    }
}