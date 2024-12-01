package MS_CLIENTE.MS_CLIENTE.service.impl;

import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;
import MS_CLIENTE.MS_CLIENTE.entity.ClientEntity;
import MS_CLIENTE.MS_CLIENTE.exception.ClientNotFoundException;
import MS_CLIENTE.MS_CLIENTE.mapper.ClientMapper;
import MS_CLIENTE.MS_CLIENTE.repository.ClientRepository;
import MS_CLIENTE.MS_CLIENTE.service.ClientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    private ClientMapper clientMapper;

    @Override
    public ClientEntity createClient(ClientDto clientDto) {
      if (clientRepository.existsByEmail(clientDto.getEmail())) {
        throw new DataIntegrityViolationException("Email already exists: " + clientDto.getEmail());
      }

        ClientEntity newClient = clientMapper.toEntity(clientDto);
        ClientEntity savedClient = clientRepository.save(newClient);
        return savedClient;
    }

    @Override
    public Optional<ClientEntity> getById(Long id) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        return Optional.of(client);
    }

    @Override
    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public ClientEntity updateClient(Long id, ClientDto clientDto) {
      ClientEntity client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);

      if (Objects.nonNull(clientDto.getEmail()) && !clientDto.getEmail().isEmpty()) {
        client.setEmail(clientDto.getEmail());
      }

      if (Objects.nonNull(clientDto.getPhone()) && !clientDto.getPhone().isEmpty()) {
        client.setPhone(clientDto.getPhone());
      }

      this.clientRepository.save(client);
        return client;
    }

    @Override
    public void deleteClient(Long id) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        clientRepository.delete(client);

    }


}
