package romario.cabo.com.br.consulta_api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import romario.cabo.com.br.consulta_api.domain.enums.ProfileEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "usuario",
        catalog = "consulta_bd",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity<Long> {
    private static final long serialVersionUID = 3490428625594717346L;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, length = 100)
    private String password;

    @Column(name = "nome", nullable = false, length = 100, unique = true)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Profile> profiles = new ArrayList<>();

    public Set<ProfileEnum> getProfileCodes() {
        Set<ProfileEnum> profiles = new HashSet<>();

        this.profiles.forEach(obj -> profiles.add(ProfileEnum.toEnum(obj.getProfileCode())));

        return profiles;
    }
}
