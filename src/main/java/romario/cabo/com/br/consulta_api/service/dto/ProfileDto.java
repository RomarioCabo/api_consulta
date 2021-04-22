package romario.cabo.com.br.consulta_api.service.dto;

import lombok.Data;
import romario.cabo.com.br.consulta_api.domain.enums.ProfileEnum;

import java.io.Serializable;

@Data
public class ProfileDto implements Serializable {
    private static final long serialVersionUID = -3742052572023139535L;

    private Long id;
    private int codProfile;
    private ProfileEnum profileType;
}


