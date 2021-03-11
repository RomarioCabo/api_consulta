package romario.cabo.com.br.consulta_api.service.mapper;

import romario.cabo.com.br.consulta_api.model.User;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.UserForm;

import java.util.List;

public interface UserMapper {

    User toEntity(UserForm form);

    List<User> toEntity(List<UserForm> forms);

    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);

}
