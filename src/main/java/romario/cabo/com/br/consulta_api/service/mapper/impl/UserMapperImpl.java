package romario.cabo.com.br.consulta_api.service.mapper.impl;

import org.springframework.stereotype.Component;
import romario.cabo.com.br.consulta_api.model.enums.ProfileEnum;
import romario.cabo.com.br.consulta_api.repository.ProfileRepository;
import romario.cabo.com.br.consulta_api.service.form.UserForm;
import romario.cabo.com.br.consulta_api.model.User;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    private final ProfileRepository profileRepository;

    public UserMapperImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

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

    @Override
    public List<UserDto> tupleToDto(List<Tuple> tuples) {
        List<UserDto> usersDto = new ArrayList<>();

        tuples.forEach(obj -> usersDto.add(getUser(obj)));

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
        userDto.setProfile(ProfileEnum.toEnum(profileRepository.findProfileCodeByIdUser(user.getId())));

        return userDto;
    }

    private UserDto getUser(Tuple tuple) {
        UserDto userDto = new UserDto();
        userDto.setId((Long) tuple.get(0));
        userDto.setName((String) tuple.get(1));
        userDto.setEmail((String) tuple.get(2));
        userDto.setProfile(ProfileEnum.toEnum(profileRepository.findProfileCodeByIdUser((Long) tuple.get(0))));

        return userDto;
    }
}