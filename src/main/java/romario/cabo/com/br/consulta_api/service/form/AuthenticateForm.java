package romario.cabo.com.br.consulta_api.service.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticateForm implements Serializable {
    private static final long serialVersionUID = -4036782556837929694L;

    private String email;
    private String password;
}
