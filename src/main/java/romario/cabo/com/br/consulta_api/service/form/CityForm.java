package romario.cabo.com.br.consulta_api.service.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityForm implements Serializable {

    private static final long serialVersionUID = 1103401658851316461L;

    private String name;
    private Long idState;
}
