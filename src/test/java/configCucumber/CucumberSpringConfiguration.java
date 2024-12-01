package configCucumber;

import MS_CLIENTE.MS_CLIENTE.MsClienteApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = MsClienteApplication.class)
@AutoConfigureMockMvc
public class CucumberSpringConfiguration {
}