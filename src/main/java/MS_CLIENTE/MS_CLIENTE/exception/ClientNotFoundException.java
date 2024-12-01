package MS_CLIENTE.MS_CLIENTE.exception;


import MS_CLIENTE.MS_CLIENTE.config.i18n.i18NConstants;

import java.util.ResourceBundle;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super(ResourceBundle.getBundle("i18n/message").getString(i18NConstants.CLIENT_NOT_FOUND));
    }
}
