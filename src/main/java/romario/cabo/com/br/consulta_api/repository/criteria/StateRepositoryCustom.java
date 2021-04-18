package romario.cabo.com.br.consulta_api.repository.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;

public interface StateRepositoryCustom {

    Page<StateDto> filterState(StateFilter state, Pageable pageable);
}
