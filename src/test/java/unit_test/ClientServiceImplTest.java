package unit_test;

import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;
import MS_CLIENTE.MS_CLIENTE.entity.ClientEntity;
import MS_CLIENTE.MS_CLIENTE.exception.ClientNotFoundException;
import MS_CLIENTE.MS_CLIENTE.mapper.ClientMapper;
import MS_CLIENTE.MS_CLIENTE.repository.ClientRepository;
import MS_CLIENTE.MS_CLIENTE.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import templateDto.ClientTemplateDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientDto clientDto;
    private ClientEntity clientEntity;

    @BeforeEach
    void setUp() {
        clientDto = ClientTemplateDto.clientTemplateDto();
        clientEntity = ClientEntity.builder()
                .id(1L)
                .email(clientDto.getEmail())
                .phone(clientDto.getPhone())
                .build();
    }

    @Test
    void createClient_ShouldThrowException_WhenEmailExists() {
        when(clientRepository.existsByEmail(clientDto.getEmail())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> clientService.createClient(clientDto));
    }

    @Test
    void createClient_ShouldSaveClient_WhenEmailDoesNotExist() {
        when(clientRepository.existsByEmail(clientDto.getEmail())).thenReturn(false);
        when(clientMapper.toEntity(clientDto)).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        ClientEntity savedClient = clientService.createClient(clientDto);

        assertNotNull(savedClient);
        assertEquals(clientEntity.getEmail(), savedClient.getEmail());
        verify(clientRepository, times(1)).save(clientEntity);
    }

    @Test
    void getById_ShouldReturnClient_WhenClientExists() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        Optional<ClientEntity> foundClient = clientService.getById(1L);

        assertTrue(foundClient.isPresent());
        assertEquals(clientEntity.getEmail(), foundClient.get().getEmail());
    }

    @Test
    void getById_ShouldThrowException_WhenClientDoesNotExist() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getById(1L));
    }

    @Test
    void getAllClients_ShouldReturnAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(clientEntity));

        List<ClientEntity> clients = clientService.getAllClients();

        assertFalse(clients.isEmpty());
        assertEquals(1, clients.size());
    }

    @Test
    void updateClient_ShouldUpdateClient_WhenClientExists() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        ClientEntity updatedClient = clientService.updateClient(1L, clientDto);

        assertNotNull(updatedClient);
        assertEquals(clientEntity.getEmail(), updatedClient.getEmail());
        verify(clientRepository, times(1)).save(clientEntity);
    }

    @Test
    void deleteClient_ShouldDeleteClient_WhenClientExists() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        doNothing().when(clientRepository).delete(clientEntity);

        clientService.deleteClient(1L);

        verify(clientRepository, times(1)).delete(clientEntity);
    }
}