package romario.cabo.com.br.consulta_api.repository;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import romario.cabo.com.br.consulta_api.model.User;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {


    private final UserRepository userRepository;
    private final TestEntityManager entityManager;

    public UserRepositoryTest(UserRepository userRepository, TestEntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    /*
    * Teste deve retornar True se o email já existir
    * */
    @Test
    public void mustReturnTrueIfEmailAlreadyExists() {
        // cenário
        User user = getUserSave();
        entityManager.persist(user);

        // ação/execução
        boolean result = userRepository.existsByEmail("romariocabo2012@gmail.com");

        // verificação
        Assertions.assertThat(result).isTrue();
    }

    /*
     * Teste deve retornar False se o email não existir
     * */
    @Test
    public void mustReturnFalseIfEmailDoesNotExist() {
        // cenário

        // ação/execução
        boolean result = userRepository.existsByEmail("romariocabo2012@gmail.com");

        // verificação
        Assertions.assertThat(result).isFalse();
    }

    /*
     * Teste deve retornar True se o id existir
     * */
    @Test
    public void mustReturnTrueIfIdExists() {
        // cenário
        User user = getUserSave();
        entityManager.persist(user);

        // ação/execução
        boolean result = userRepository.existsById(1L);

        // verificação
        Assertions.assertThat(result).isTrue();
    }

    /*
     * Teste deve retornar False se o id não existir
     * */
    @Test
    public void mustReturnFalseIfIdDoesNotExist() {
        // cenário

        // ação/execução
        boolean result = userRepository.existsById(1L);

        // verificação
        Assertions.assertThat(result).isFalse();
    }

    /*
     * Teste deve retornar diferente de nulo se o id existir
     * */
    @Test
    public void mustReturnNotNullIfIdExists() {
        // cenário
        User user = getUserSave();
        user = entityManager.persist(user);

        // ação/execução
        user = userRepository.findUser(user.getId());

        // verificação
        Assertions.assertThat(user).isNotNull();
    }

    /*
     * Teste deve retornar nulo se o id existir
     * */
    @Test
    public void mustReturnNullIfIdDoesNotExists() {
        // cenário

        // ação/execução
        User user = userRepository.findUser(1L);

        // verificação
        Assertions.assertThat(user).isNull();
    }

    /*
     * Teste deve retornar diferente de nulo se o id existir
     * */
    @Test
    public void mustReturnNotNullIfEmailExists() {
        // cenário
        User user = getUserSave();
        user = entityManager.persist(user);

        // ação/execução
        user = userRepository.findByEmail(user.getEmail());

        // verificação
        Assertions.assertThat(user).isNotNull();
    }

    /*
     * Teste deve retornar nulo se o id existir
     * */
    @Test
    public void mustReturnNullIfEmailDoesNotExists() {
        // cenário

        // ação/execução
        User user = userRepository.findByEmail("romariocabo2012@gmail.com");

        // verificação
        Assertions.assertThat(user).isNull();
    }

    private User getUserSave() {
        User user = new User();
        user.setEmail("romariocabo2012@gmail.com");
        user.setPassword("123");
        user.setName("Romário Cabó");

        return user;
    }
}
