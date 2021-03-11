package romario.cabo.com.br.consulta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import romario.cabo.com.br.consulta_api.repository.criteria.StateRepositoryCustom;
import romario.cabo.com.br.consulta_api.model.State;

import javax.transaction.Transactional;

@Repository
public interface StateRepository extends JpaRepository<State, Long>, StateRepositoryCustom {

    @Transactional
    boolean existsByAcronymAndName(String acronym, String name);

    @Transactional
    boolean existsById(Long id);

}
