package MS_CLIENTE.MS_CLIENTE.service;

import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;
import MS_CLIENTE.MS_CLIENTE.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public ClientEntity createClient(ClientDto clientDto);

    public Optional<ClientEntity> getById(Long id);

    public List<ClientEntity> getAllClients();

    public ClientEntity updateClient(Long id, ClientDto clientDto);

    public void deleteClient(Long id);

}
