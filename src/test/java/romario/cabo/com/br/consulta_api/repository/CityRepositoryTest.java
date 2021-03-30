package romario.cabo.com.br.consulta_api.repository;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import romario.cabo.com.br.consulta_api.model.City;
import romario.cabo.com.br.consulta_api.model.State;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    StateRepository stateRepository;

    /*
     * Teste deve retornar Not Null se o nome da cidade e id do estado existeirem
     * */
    @Test
    public void mustReturnNotNullIfNameAndIdStateAlreadyExists() {
        // cenário
        stateRepository.deleteAll();

        State state = stateRepository.save(getState(null));

        cityRepository.deleteAll();
        City city = cityRepository.save(getCity(state.getId()));

        // ação/execução
        City result = cityRepository.existsCity(city.getName(), state.getId());

        // verificação
        Assertions.assertThat(result.getId()).isNotNull();
    }

    /*
     * Teste deve retornar Null se o nome da cidade e id do estado não existirem
     * */
    @Test
    public void mustReturnNullIfNameAndIdStateDoesNotExist() {
        // cenário
        stateRepository.deleteAll();

        cityRepository.deleteAll();

        // ação/execução
        City result = cityRepository.existsCity("Russas", 1L);

        // verificação
        Assertions.assertThat(result).isNull();
    }

    /*
     * Teste deve retornar diferente de nulo se o nome da cidade e o id estado existir
     * */
    @Test
    public void mustReturnNotNullIfNameCityAndStateIdExists() {
        // cenário
        stateRepository.deleteAll();

        State state = getState(null);
        state = stateRepository.save(state);

        cityRepository.deleteAll();

        City city = getCity(state.getId());
        cityRepository.save(city);

        // ação/execução
        city = cityRepository.existsCity("Russas", state.getId());

        // verificação
        Assertions.assertThat(city).isNotNull();
    }

    /*
     * Teste deve retornar e nulo se o nome da cidade e o id estado não existir
     * */
    @Test
    public void mustReturnNotNullIfNameCityAndStateIdDoesNotExists() {
        // cenário
        stateRepository.deleteAll();
        cityRepository.deleteAll();

        // ação/execução
        City city = cityRepository.existsCity("Russas", 1L);

        // verificação
        Assertions.assertThat(city).isNull();
    }

    /*
     * Teste deve retornar true se o id da cidade existir
     * */
    @Test
    public void mustReturnTrueIfIdExists() {
        // cenário
        stateRepository.deleteAll();

        State state = getState(null);
        stateRepository.save(state);

        cityRepository.deleteAll();

        City city = getCity(state.getId());
        cityRepository.save(city);

        // ação/execução
        boolean result = cityRepository.existsById(city.getId());

        // verificação
        Assertions.assertThat(result).isTrue();
    }

    /*
     * Teste deve retornar False se a sigla e o nome do estado não existirem
     * */
    @Test
    public void mustReturnFalseIfIdDoesNotExist() {
        // cenário
        stateRepository.deleteAll();

        cityRepository.deleteAll();

        // ação/execução
        boolean result = cityRepository.existsById(1L);

        // verificação
        Assertions.assertThat(result).isFalse();
    }

    /*
     * Teste deve salvar uma cidade
     * */
    @Test
    public void shouldCityPersistInDatabase() {
        // cenário
        stateRepository.deleteAll();

        State state = stateRepository.save(getState(null));

        cityRepository.deleteAll();

        City city = cityRepository.save(getCity(state.getId()));

        // verificacao
        Assertions.assertThat(city.getId()).isNotNull();
    }

    private City getCity(Long id) {
        City city = new City();
        city.setName("Russas");
        city.setState(getState(id));

        return city;
    }

    private State getState(Long id) {
        State state = new State();
        state.setId(id);
        state.setName("Ceará");
        state.setAcronym("CE");
        state.setImage("teste.png");
        state.setCapital("Fortaleza");
        state.setGentle("Cearense");
        state.setTerritorialArea(149894);
        state.setTotalCounties(184);
        state.setTotalPopulation(9075649);
        state.setDemographicDensity(56.76);
        state.setIdh(0.735);
        state.setBorderingTerritory("Piauí, Rio Grande do Norte, Paraíba e Pernambuco");
        state.setPib(1383790000);
        state.setNaturalAspects("Clima — semiárido e tropical; Relevo — chapadas e cuestas; Vegetação — domínio da Caatinga.");
        state.setEconomicActivities("Predomínio de atividade do setor terciário (comércio e serviços) e grande destaque para o turismo.");
        state.setCuriosity("O estado é conhecido por ser o berço dos maiores humoristas do Brasil");
        state.setRegion("Nordeste");

        return state;
    }
}
