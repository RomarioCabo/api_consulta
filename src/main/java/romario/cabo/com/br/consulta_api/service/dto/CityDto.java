package romario.cabo.com.br.consulta_api.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityDto implements Serializable {
    private static final long serialVersionUID = 7013913336817621382L;

    private Long id;
    private String name;
    private StateDto state;

}
