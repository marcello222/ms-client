package integration_test;

import MS_CLIENTE.MS_CLIENTE.MsClienteApplication;
import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;
import MS_CLIENTE.MS_CLIENTE.entity.ClientEntity;
import MS_CLIENTE.MS_CLIENTE.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import templateDto.ClientTemplateDto;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MsClienteApplication.class)
@AutoConfigureMockMvc
class ClientServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClient() throws Exception {
        ClientDto clientDto = ClientTemplateDto.clientTemplateDto();
        ClientEntity clientEntity = ClientEntity.builder()
                .id(1L)
                .email(clientDto.getEmail())
                .phone(clientDto.getPhone())
                .build();

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(clientDto.getEmail()));
    }

    @Test
    void testGetClientById() throws Exception {
        Long id = 1L;
        ClientEntity clientEntity = ClientEntity.builder()
                .id(id)
                .email("exempla@exemple.com")
                .phone("33 33333 33333")
                .build();

        when(clientRepository.findById(id)).thenReturn(Optional.of(clientEntity));

        mockMvc.perform(get("/clients/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("exempla@exemple.com"));
    }

    @Test
    void testDeleteClient() throws Exception {
        // Create a client
        ClientDto clientDto = ClientTemplateDto.clientTemplateDto();
        ClientEntity clientEntity = ClientEntity.builder()
                .id(1L)
                .email(clientDto.getEmail())
                .phone(clientDto.getPhone())
                .build();

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isCreated());

        // Delete the client
        mockMvc.perform(delete("/clients/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}