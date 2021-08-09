package romario.cabo.com.br.consulta_api.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import romario.cabo.com.br.consulta_api.domain.User;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.exception.InternalServerErrorException;
import romario.cabo.com.br.consulta_api.exception.NotFoundException;
import romario.cabo.com.br.consulta_api.exception.UnauthorizedException;
import romario.cabo.com.br.consulta_api.repository.UserRepository;
import romario.cabo.com.br.consulta_api.security.JWTUtil;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.AuthenticateForm;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;

@Service
@Transactional
public class AuthenticateService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final JWTUtil jwtUtil;

  public AuthenticateService(
      UserRepository userRepository,
      UserMapper userMapper, JWTUtil jwtUtil) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.jwtUtil = jwtUtil;
  }

  public UserDto authenticate(AuthenticateForm form) {
    User user = validateEmail(form.getEmail());

    firstAccess(form.getEmail());

    validateCredentials(form.getPassword(), user.getPassword());

    return mapperUser(user);
  }

  private User validateEmail(String email) {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new NotFoundException("E-mail informado não localizado em nossa Base de Dados!");
    }

    return user;
  }

  private void firstAccess(String email) {
    if (email.equals("admin@admin.com")) {
      List<User> users = userRepository.findAll();

      if (users.size() >= 2) {
        throw new BadRequestException(
            "O usuário: " + email
                + " é apenas para o primeiro acesso!\nPor favor! Use outro usuário!");
      }
    }
  }

  private void validateCredentials(String inputPassword, String encryptedPassword) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    if (!passwordEncoder.matches(inputPassword, encryptedPassword)) {
      throw new UnauthorizedException("Credenciais inválidas!");
    }
  }

  private UserDto mapperUser(User user) {
    try {
      String token = jwtUtil.generateToken(user.getEmail());

      UserDto userDto = userMapper.toDto(user);
      userDto.setToken("Bearer " + token);

      return userDto;
    } catch (Exception e) {
      throw new InternalServerErrorException("Não foi possivel retornar os dados!");
    }
  }
}
