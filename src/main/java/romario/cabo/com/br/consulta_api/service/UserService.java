package romario.cabo.com.br.consulta_api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.repository.UserRepository;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.UserForm;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;
import romario.cabo.com.br.consulta_api.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService implements Crud<UserDto, UserForm, UserFilter> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
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
            user = userRepository.save(getUser(form, id));
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar!");
        }

        try {
            return userMapper.toDto(user);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para DTO!");
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

    private User getUser(UserForm form, Long id) {
        try {
            User user;
            user = userMapper.toEntity(form);
            user.setPassword(encryptPassword(user.getPassword()));

            if (id != null) {
                user.setId(id);
            }

            return user;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para entidade!");
        }
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
