package romario.cabo.com.br.consulta_api.repository.criteria;

import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;

import java.util.List;

public interface StateRepositoryCustom {

    List<StateDto> filterState(StateFilter state);
}
