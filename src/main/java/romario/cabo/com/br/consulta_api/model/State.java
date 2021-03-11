package romario.cabo.com.br.consulta_api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "estado",
        catalog = "consulta_bd",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "sigla"})
)
@Data
public class State implements Serializable {

    private static final long serialVersionUID = 3867135242328320754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado", unique = true, nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 45, unique = true)
    private String name;

    @Column(name = "sigla", nullable = false, length = 2, unique = true)
    private String acronym;

    @Column(name = "imagem")
    private String image;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();
}
