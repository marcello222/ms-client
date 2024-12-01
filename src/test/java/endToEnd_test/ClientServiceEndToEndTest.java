package endToEnd_test;

import MS_CLIENTE.MS_CLIENTE.MsClienteApplication;
import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import templateDto.ClientTemplateDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MsClienteApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientServiceEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientDto clientDto;

    @BeforeEach
    void setUp() throws Exception {
        clientDto = ClientTemplateDto.clientTemplateDto();
    }

    @Test
    void testCreateClient() throws Exception {
        String clientJson = objectMapper.writeValueAsString(clientDto);

        String responseContent = mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ClientDto actualDto = objectMapper.readValue(responseContent, ClientDto.class);
        assertEquals(clientDto.getEmail(), actualDto.getEmail());
    }

    @Test
    void testGetClientById() throws Exception {
        String clientJson = objectMapper.writeValueAsString(clientDto);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());

        String responseContent = mockMvc.perform(get("/clients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ClientDto actualDto = objectMapper.readValue(responseContent, ClientDto.class);
        assertEquals(clientDto.getEmail(), actualDto.getEmail());
    }

    @Test
    void testDeleteClient() throws Exception {
        String clientJson = objectMapper.writeValueAsString(clientDto);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/clients/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteClient_NotFound() throws Exception {
        String clientJson = objectMapper.writeValueAsString(clientDto);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/clients/{id}", 1L))
                .andExpect(status().isNoContent());

        String responseContent = mockMvc.perform(get("/clients/{id}", 1L))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("{O} Cliente não foi encontrado", responseContent);
    }
}