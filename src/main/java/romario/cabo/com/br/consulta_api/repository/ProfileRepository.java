package romario.cabo.com.br.consulta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import romario.cabo.com.br.consulta_api.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
