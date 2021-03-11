package romario.cabo.com.br.consulta_api.repository.criteria;

import romario.cabo.com.br.consulta_api.repository.criteria.filter.CityFilter;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;

import java.util.List;

public interface CityRepositoryCustom {

    List<CityDto> filterCity(CityFilter filter);
}
