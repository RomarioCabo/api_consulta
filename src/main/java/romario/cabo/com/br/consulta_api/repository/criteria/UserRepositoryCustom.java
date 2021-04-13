package romario.cabo.com.br.consulta_api.repository.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;

import java.util.List;

public interface UserRepositoryCustom {
    Page<UserDto> filterUser(UserFilter filter, Pageable pageable);
}
