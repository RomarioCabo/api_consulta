package romario.cabo.com.br.consulta_api.repository.criteria.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityFilter implements Serializable {
    private static final long serialVersionUID = 5530715245601959575L;

    private Long idCity;
    private String nameCity;

    private Long idState;
    private String nameState;
    private String acronymState;
}
