package romario.cabo.com.br.consulta_api.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import romario.cabo.com.br.consulta_api.model.State;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class StateRepositoryTest {

    @Autowired
    StateRepository stateRepository;

    /*
     * Teste deve retornar True se a sigla e o nome do estado já existirem
     * */
    @Test
    public void mustReturnTrueIfAcronymAndNameAlreadyExists() {
        // cenário
        stateRepository.deleteAll();

        State state = getState();
        state = stateRepository.save(state);

        // ação/execução
        boolean result = stateRepository.existsByAcronymAndName(state.getAcronym(), state.getName());

        // verificação
        Assertions.assertTrue(result);
    }

    /*
     * Teste deve retornar False se a sigla e o nome do estado não existirem
     * */
    @Test
    public void mustReturnFalseIfAcronymAndNameDoesNotExist() {
        // cenário
        stateRepository.deleteAll();

        // ação/execução
        boolean result = stateRepository.existsByAcronymAndName("CE", "Ceará");

        // verificação
        Assertions.assertFalse(result);
    }

    /*
     * Teste deve retornar True se a sigla e o nome do estado já existirem
     * */
    @Test
    public void mustReturnTrueIfIdAlreadyExists() {
        // cenário
        stateRepository.deleteAll();

        State state = getState();
        state = stateRepository.save(state);

        // ação/execução
        boolean result = stateRepository.existsById(state.getId());

        // verificação
        Assertions.assertTrue(result);
    }

    /*
     * Teste deve retornar False se a sigla e o nome do estado não existirem
     * */
    @Test
    public void mustReturnFalseIfIdDoesNotExist() {
        // cenário
        stateRepository.deleteAll();

        // ação/execução
        boolean result = stateRepository.existsById(1L);

        // verificação
        Assertions.assertFalse(result);
    }

    private State getState() {
        State state = new State();
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
