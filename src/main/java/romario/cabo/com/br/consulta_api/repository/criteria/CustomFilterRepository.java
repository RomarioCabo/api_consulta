package romario.cabo.com.br.consulta_api.repository.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.Selection;

public interface CustomFilterRepository<DTO, FILTER, ENTITY> {

    Page<DTO> filterCity(FILTER filter, Pageable pageable, Class<ENTITY> entity, Selection<?>... selections);
}
