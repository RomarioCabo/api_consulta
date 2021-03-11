package romario.cabo.com.br.consulta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import romario.cabo.com.br.consulta_api.repository.criteria.CityRepositoryCustom;
import romario.cabo.com.br.consulta_api.model.City;


@Repository
public interface CityRepository extends JpaRepository<City, Long>, CityRepositoryCustom {

    @Transactional(readOnly = true)
    @Query("SELECT city FROM City city WHERE city.name=:name AND city.state.id=:idState")
    City existsCity(@Param("name") String name, @Param("idState") Long idState);

    @Transactional(readOnly = true)
    boolean existsById(Long id);
}
