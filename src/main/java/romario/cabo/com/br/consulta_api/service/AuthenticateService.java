package romario.cabo.com.br.consulta_api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.exception.UnauthorizedException;
import romario.cabo.com.br.consulta_api.repository.UserRepository;
import romario.cabo.com.br.consulta_api.service.form.AuthenticateForm;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;
import romario.cabo.com.br.consulta_api.model.User;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;

@Service
public class AuthenticateService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticateService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto authenticateUser(AuthenticateForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User user = userRepository.findByEmail(form.getEmail());

            if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
                return userMapper.toDto(user);
            } else {
                throw new UnauthorizedException("Usuario não autorizado!");
            }
        } else {
            throw new BadRequestException("E-Mail informado não encontrado!");
        }
    }
}
