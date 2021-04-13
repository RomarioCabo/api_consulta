package romario.cabo.com.br.consulta_api.repository.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.CityFilter;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;

public interface CityRepositoryCustom {

    Page<CityDto> filterCity(CityFilter filter, Pageable pageable);
}
