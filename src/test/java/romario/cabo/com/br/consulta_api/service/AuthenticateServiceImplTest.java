package romario.cabo.com.br.consulta_api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import romario.cabo.com.br.consulta_api.repository.UserRepository;
import romario.cabo.com.br.consulta_api.service.impl.AuthenticateServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthenticateServiceImplTest {

    @SpyBean
    AuthenticateServiceImpl authenticate;

    @MockBean
    UserRepository repository;

    /*
     * deve retornar verdadeiro se as senhas coincidirem
     */
    @Test
    public void mustReturnTrueIfPasswordsMatch() {
        // cenário
        Mockito.when(authenticate.passwordsMatch(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        
    }
}
