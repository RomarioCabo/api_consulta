package romario.cabo.com.br.consulta_api.service.mapper.impl;

import org.springframework.stereotype.Component;
import romario.cabo.com.br.consulta_api.service.form.UserForm;
import romario.cabo.com.br.consulta_api.model.User;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserForm form) {
        return getUser(form);
    }

    @Override
    public List<User> toEntity(List<UserForm> forms) {
        List<User> users = new ArrayList<>();

        forms.forEach(obj -> users.add(getUser(obj)));

        return users;
    }

    @Override
    public UserDto toDto(User user) {
        return getUser(user);
    }

    @Override
    public List<UserDto> toDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();

        users.forEach(obj -> usersDto.add(getUser(obj)));

        return usersDto;
    }

    private User getUser(UserForm form) {
        User user = new User();
        user.setId(null);
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());

        return user;
    }

    private UserDto getUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
