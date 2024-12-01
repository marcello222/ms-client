package MS_CLIENTE.MS_CLIENTE.mapper;

import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;
import MS_CLIENTE.MS_CLIENTE.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    ClientEntity toEntity(ClientDto clientDto);

    ClientDto toDto(ClientEntity clientEntity);


}
