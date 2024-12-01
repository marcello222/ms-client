package templateDto;

import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;

public class ClientTemplateDto {

    public static ClientDto clientTemplateDto() {
        return ClientDto.builder()
                .name("Joao Alberto")
                .email("exempla@exemple.com")
                .phone("33 33333 33333")
                .build();
    }

}
