package romario.cabo.com.br.consulta_api.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 635202739566101055L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;
}
