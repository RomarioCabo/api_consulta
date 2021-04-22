package romario.cabo.com.br.consulta_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.exception.InternalServerErrorException;
import romario.cabo.com.br.consulta_api.repository.UserRepository;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.service.ProfileService;
import romario.cabo.com.br.consulta_api.service.ServiceInterface;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.UserForm;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;
import romario.cabo.com.br.consulta_api.domain.User;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements ServiceInterface<UserDto, UserForm, UserFilter> {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, ProfileService profileService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.profileService = profileService;
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
            throw new InternalServerErrorException("Não foi possível salvar o usuário!");
        }

        profileService.save(null, user, form.getCodProfile());

        try {
            return userMapper.toDto(user);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public UserDto update(UserForm form, Long idUser, Long idProfile) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new BadRequestException("Usuário não localizado em nossa base de dados!"));

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

        profileService.save(idProfile, user, form.getCodProfile());

        try {
            return userMapper.toDto(user);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public void delete(Long... params) {
        if (params.length >= 2 && params[1] != null) {
            profileService.delete(params[1]);
        }

        if (params.length >= 1 && params[0] != null) {
            userRepository.findById(params[0])
                    .orElseThrow(() -> new BadRequestException("Usuário não localizado em nossa base de dados!"));

            try {
                userRepository.deleteById(params[0]);
            } catch (Exception e) {
                throw new InternalServerErrorException("Não foi possível excluir! " + e.getMessage());
            }
        }
    }

    @Override
    public Page<UserDto> findAll(UserFilter filter, Integer page, Integer linesPerPage, String sortBy) {
        try {
            Pageable pageable = PageRequest.of(page, linesPerPage, Sort.by(sortBy));

            Page<UserDto> usersPage = userRepository.filterUser(filter, pageable);

            if (usersPage.isEmpty()) {
                return null;
            }

            return usersPage;
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível retornar os dados! \nError: " + e.getMessage());
        }
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
