package romario.cabo.com.br.consulta_api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(
        name = "perfil",
        catalog = "consulta_bd"
)
@Data
@EqualsAndHashCode(callSuper = true)
public class Profile extends AbstractEntity<Long> {
    private static final long serialVersionUID = -2924424353341713173L;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    @Column(name = "cod_perfil", nullable = false, length = 11)
    private int profileCode;
}
