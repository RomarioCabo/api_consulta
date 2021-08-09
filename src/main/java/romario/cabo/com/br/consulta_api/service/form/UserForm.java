package romario.cabo.com.br.consulta_api.service.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserForm implements Serializable {
    private static final long serialVersionUID = -8391934719567492918L;

    private String name;
    private String email;
    private String password;
    private int codProfile;
}
