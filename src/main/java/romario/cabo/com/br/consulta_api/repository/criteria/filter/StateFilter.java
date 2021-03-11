package romario.cabo.com.br.consulta_api.repository.criteria.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class StateFilter implements Serializable {
    private static final long serialVersionUID = -3096328773666709282L;

    private Long id;
    private String name;
    private String acronym;
}
