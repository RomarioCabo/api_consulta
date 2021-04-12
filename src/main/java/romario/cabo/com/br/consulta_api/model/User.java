package romario.cabo.com.br.consulta_api.model;

import lombok.Data;
import romario.cabo.com.br.consulta_api.model.enums.ProfileEnum;

import javax.persistence.*;
import java.io.Serializable;
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
public class User implements Serializable {
    private static final long serialVersionUID = 3490428625594717346L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", unique = true, nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, length = 100)
    private String password;

    @Column(name = "nome", nullable = false, length = 100, unique = true)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    private List<Profile> profiles = new ArrayList<>();

    public Set<ProfileEnum> getProfileCodes() {
        Set<ProfileEnum> profiles = new HashSet<>();

        this.profiles.forEach(obj -> profiles.add(ProfileEnum.toEnum(obj.getProfile())));

        return profiles;
    }

    /*public void addProfile(Profile profile) {
        profiles.add(profile.getCod());
    }*/
}
