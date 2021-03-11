package romario.cabo.com.br.consulta_api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "cidade",
        catalog = "consulta_bd"
)
@Data
public class City implements Serializable {

    private static final long serialVersionUID = 9058667355574191710L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cidade", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private State state;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;
}
