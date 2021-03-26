package romario.cabo.com.br.consulta_api.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import romario.cabo.com.br.consulta_api.model.City;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    private City getCity() {
        City city = new City();

        return city;
    }
 }
