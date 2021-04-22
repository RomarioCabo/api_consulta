package romario.cabo.com.br.consulta_api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(
        name = "cidade",
        catalog = "consulta_bd"
)
@Data
@EqualsAndHashCode(callSuper = true)
public class City extends AbstractEntity<Long> {

    private static final long serialVersionUID = 9058667355574191710L;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private State state;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;
}
