package steps;

import MS_CLIENTE.MS_CLIENTE.MsClienteApplication;
import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import templateDto.ClientTemplateDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MsClienteApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientDto clientDto;
    private MvcResult result;

    @Before
    public void setUp() {
        clientDto = ClientTemplateDto.clientTemplateDto();
    }

    @Given("a client with email {string}")
    public void a_client_with_email(String email) {
        clientDto.setEmail(email);
    }

    @When("I create the client")
    public void i_create_the_client() throws Exception {
        String clientJson = objectMapper.writeValueAsString(clientDto);
        result = mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Then("the client should be created successfully")
    public void the_client_should_be_created_successfully() throws Exception {
        ClientDto actualDto = objectMapper.readValue(result.getResponse().getContentAsString(), ClientDto.class);
        assertEquals(clientDto.getEmail(), actualDto.getEmail());
    }


}