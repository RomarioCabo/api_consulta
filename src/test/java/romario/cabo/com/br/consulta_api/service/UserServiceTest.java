package romario.cabo.com.br.consulta_api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.model.User;
import romario.cabo.com.br.consulta_api.repository.UserRepository;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.UserForm;
import romario.cabo.com.br.consulta_api.service.impl.UserServiceImpl;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @SpyBean
    UserServiceImpl service;

    @MockBean
    UserRepository repository;

    @Autowired
    UserMapper userMapper;

    /*
     * Deve salvar um usuário
     */
    @Test
    public void mustSaveUser() {
        // cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        User user = toUser(getUserForm());

        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);

        // ação
        UserDto userSave = service.save(getUserForm(), null);

        //verificao
        Assertions.assertThat(userSave).isNotNull();
        Assertions.assertThat(userSave.getName()).isEqualTo("Romário Corderio Cabó");
        Assertions.assertThat(userSave.getEmail()).isEqualTo("romariocabo2012@gmail.com");
    }

    /*
     * Não deve salvar um usuário com o email já registardo
     */
    @Test
    public void shouldNotSaveUsersWithEmailAlreadyRegistered() {
        //cenario
        String email = "romariocabo2012@gmail.com";
        User user = toUser(getUserForm());
        Mockito.doThrow(BadRequestException.class).when(repository).existsByEmail(email);

        //acao
        org.junit.jupiter.api.Assertions
                .assertThrows(BadRequestException.class, () -> service.save(getUserForm(), null));

        //verificacao
        Mockito.verify(repository, Mockito.never()).save(user);
    }

    private UserForm getUserForm() {
        UserForm form = new UserForm();
        form.setName("Romário Corderio Cabó");
        form.setEmail("romariocabo2012@gmail.com");
        form.setPassword("123");

        return form;
    }

    private User toUser(UserForm form) {
        return userMapper.toEntity(form);
    }
}
