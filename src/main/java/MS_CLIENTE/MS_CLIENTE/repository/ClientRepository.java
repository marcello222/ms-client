package MS_CLIENTE.MS_CLIENTE.repository;

import MS_CLIENTE.MS_CLIENTE.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    boolean existsByEmail(String email);
}
