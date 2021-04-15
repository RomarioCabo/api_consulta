package romario.cabo.com.br.consulta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import romario.cabo.com.br.consulta_api.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT profile FROM Profile profile WHERE profile.user.id=:idUser")
    Profile findProfileByIdUser(Long idUser);
}
