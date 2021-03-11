package romario.cabo.com.br.consulta_api.repository.criteria;

import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;

import java.util.List;

public interface UserRepositoryCustom {

    List<UserDto> filterUser(UserFilter filter);
}
