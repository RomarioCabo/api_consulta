package romario.cabo.com.br.consulta_api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "perfil",
        catalog = "consulta_bd"
)
@Data
public class Profile implements Serializable {
    private static final long serialVersionUID = -2924424353341713173L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    @Column(name = "perfil", nullable = false, length = 11)
    private int profile;
}
