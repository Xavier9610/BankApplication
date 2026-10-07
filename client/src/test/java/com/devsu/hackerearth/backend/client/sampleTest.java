package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.service.ClientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest
public class sampleTest {

	private ClientService clientService = mock(ClientService.class);
	private ClientController clientController = new ClientController(clientService);

    @Test
    void createClientTest() {
        // Arrange
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.create(newClient)).thenReturn(createdClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.create(newClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
    }
}

// ==========================================
// F5: PRUEBA UNITARIA (No levanta Spring, usa Mockito)
// ==========================================
@ExtendWith(MockitoExtension.class)
class ClientUnitTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    @SuppressWarnings("null")
    void testCreateClientUnit() {
        // Arrange (Preparar)
        ClientDto dto = new ClientDto();
        dto.setName("Ana");
        dto.setDni("12345678");
        dto.setActive(true);

        Client clientEntity = new Client();
        clientEntity.setId(1L);
        clientEntity.setName("Ana");
        clientEntity.setDni("12345678");
        clientEntity.setActive(true);

        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);

        // Act (Actuar)
        ClientDto result = clientService.create(dto);

        // Assert (Afirmar)
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ana", result.getName());
        assertEquals("12345678", result.getDni());
    }
}

// ==========================================
// F6: PRUEBA DE INTEGRACIÓN (Levanta Spring y simula HTTP con MockMvc)
// ==========================================
@SpringBootTest
@AutoConfigureMockMvc
class ClientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SuppressWarnings("null")
    void testCreateClientIntegration() throws Exception {
        // Arrange
        ClientDto dto = new ClientDto();
        dto.setName("Carlos");
        dto.setDni("87654321");
        dto.setGender("M");
        dto.setAge(35);
        dto.setAddress("Av. Principal 123");
        dto.setPhone("555-0199");
        dto.setPassword("secretPass");
        dto.setActive(true);

        String jsonPayload = objectMapper.writeValueAsString(dto);

        // Act & Assert
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isCreated()) // Espera un 201 CREATED
                .andExpect(jsonPath("$.name").value("Carlos"))
                .andExpect(jsonPath("$.dni").value("87654321"))
                .andExpect(jsonPath("$.isActive").value(true));
    }
}
