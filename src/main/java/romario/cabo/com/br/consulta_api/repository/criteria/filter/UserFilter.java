package romario.cabo.com.br.consulta_api.repository.criteria.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserFilter implements Serializable {
    private static final long serialVersionUID = -2106696448613469943L;

    private Long id;
    private String email;
}
