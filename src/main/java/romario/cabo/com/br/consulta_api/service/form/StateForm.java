package romario.cabo.com.br.consulta_api.service.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class StateForm implements Serializable {
    private static final long serialVersionUID = -255081541401082491L;

    private String name;
    private String acronym;
    private String fileInBase64;
}
