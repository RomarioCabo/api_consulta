package romario.cabo.com.br.consulta_api.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = -2802546240485977184L;

    private Long id;
    private String name;
    private String email;
    private String token;
    private ProfileDto profile;
}
