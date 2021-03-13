package romario.cabo.com.br.consulta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import romario.cabo.com.br.consulta_api.repository.criteria.UserRepositoryCustom;
import romario.cabo.com.br.consulta_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Transactional(readOnly = true)
    boolean existsByEmail(String email);

    @Transactional(readOnly = true)
    boolean existsById(Long id);

    @Transactional(readOnly = true)
    @Query("SELECT user FROM User user WHERE user.id=:id")
    User findUser(Long id);

    @Transactional(readOnly = true)
    User findByEmail(String email);
}
