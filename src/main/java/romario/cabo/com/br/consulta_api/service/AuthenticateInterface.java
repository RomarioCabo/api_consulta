package romario.cabo.com.br.consulta_api.service;

import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.AuthenticateForm;

public interface AuthenticateInterface {

    UserDto authenticateUser(AuthenticateForm form);
}
