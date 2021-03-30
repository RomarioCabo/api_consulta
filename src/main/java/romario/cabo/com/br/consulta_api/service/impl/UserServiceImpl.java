package romario.cabo.com.br.consulta_api.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.exception.InternalServerErrorException;
import romario.cabo.com.br.consulta_api.repository.UserRepository;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.service.ServiceInterface;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.UserForm;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;
import romario.cabo.com.br.consulta_api.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements ServiceInterface<UserDto, UserForm, UserFilter> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto save(UserForm form, Long id) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new BadRequestException("E-Mail ja cadastrado!");
        }

        User user;

        try {
            user = userRepository.save(getUser(form));
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível salvar!");
        }

        try {
            return userMapper.toDto(user);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public UserDto update(UserForm form, Long id) {
        User user = userRepository.findUser(id);

        if (user == null) {
            throw new BadRequestException("Usuário não localizado em nossa base de dados!");
        }

        if (form.getEmail() != null) {

            if (userRepository.existsByEmail(form.getEmail())) {
                throw new BadRequestException("E-Mail ja cadastrado!");
            }

            user.setEmail(form.getEmail());
        }

        if (form.getPassword() != null) {
            user.setPassword(encryptPassword(form.getPassword()));
        }

        if (form.getName() != null) {
            user.setName(form.getName());
        }

        try {
            return userMapper.toDto(user);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("Usuário não encontrado!");
        }

        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> findAll(UserFilter filter) {
        return userRepository.filterUser(filter);
    }

    private User getUser(UserForm form) {
        try {
            User user;
            user = userMapper.toEntity(form);
            user.setPassword(encryptPassword(user.getPassword()));

            return user;
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para entidade!");
        }
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
