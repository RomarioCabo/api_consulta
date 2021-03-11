package romario.cabo.com.br.consulta_api.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class StandardError implements Serializable {
    private static final long serialVersionUID = 5139057664613866299L;

    private Integer status;
    private String menssagem;
    private Long timeStamp;

    public StandardError(Integer status, String menssagem, Long timeStamp) {
        this.status = status;
        this.menssagem = menssagem;
        this.timeStamp = timeStamp;
    }
}
