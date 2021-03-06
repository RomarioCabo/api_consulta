package romario.cabo.com.br.consulta_api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.exception.InternalServerErrorException;
import romario.cabo.com.br.consulta_api.domain.User;
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

    @SpyBean
    UserMapper userMapper;

    /*
     * Deve salvar um usuário
     */
    @Test
    void mustSaveUser() {
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
    void shouldNotSaveUserWithEmailAlreadyRegistered() {
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

    /*
     * deve lançar uma exceção ao tentar salvar um usuário com o email já cadastrado
     */
    @Test
    void shouldThrowExceptionWhenTryingSaveUserWithEmailAlreadyRegistered() {
        //cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //acao
        Throwable exception = Assertions.catchThrowable(() -> service.save(getUserForm(), null));

        //verificacao
        Assertions.assertThat(exception)
                .isInstanceOf(BadRequestException.class)
                .hasMessage("E-Mail ja cadastrado!");
    }

    /*
     * deve lançar uma exceção quando tentar salvar na base de dados
     */
    @Test
    void shouldThrowExceptionWhenTryingSaveDatabase() {
        //cenário
        Mockito.doThrow(new InternalServerErrorException("Não foi possível salvar!")).when(service).save(getUserForm(), null);

        //acao
        Throwable exception = Assertions.catchThrowable(() -> service.save(getUserForm(), null));

        //verificacao
        Assertions.assertThat(exception)
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Não foi possível salvar!");
    }

    /*
     * deve lançar uma exceção ao tentar converter de entidade para dto
     */
    @Test
    void shouldThrowExceptionWhenTryingEntityToDto() {
        //cenário
        Mockito.doThrow(new InternalServerErrorException("Não foi possível realizar o Mapper para DTO!")).when(userMapper).toDto(getUser());

        //acao
        Throwable exception = Assertions.catchThrowable(() -> userMapper.toDto(getUser()));

        //verificacao
        Assertions.assertThat(exception)
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Não foi possível realizar o Mapper para DTO!");
    }

    /*
     * Não deve salvar um usuário
     */
    @Test
    public void shouldNotSaveUser() {
        //cenario
        User user = toUser(getUserForm());
        Mockito.doThrow(InternalServerErrorException.class).when(repository).save(user);

        //acao
        org.junit.jupiter.api.Assertions
                .assertThrows(InternalServerErrorException.class, () -> service.save(getUserForm(), null));

        //verificacao
        Mockito.verify(repository, Mockito.never()).save(user);
    }

    /*
     * Ao tentar salvar não deve realizar o mapper para o dto
     */
    @Test
    public void trySaveMustNotPerformMapperForDto() {
        //cenario
        User user = toUser(getUserForm());
        Mockito.doThrow(InternalServerErrorException.class).when(userMapper).toDto(user);

        //acao
        org.junit.jupiter.api.Assertions
                .assertThrows(InternalServerErrorException.class, () -> service.save(getUserForm(), null));

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

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Romário Corderio Cabó");
        user.setEmail("romariocabo2012@gmail.com");
        user.setPassword("123");

        return user;
    }

    private User toUser(UserForm form) {
        return userMapper.toEntity(form);
    }
}
