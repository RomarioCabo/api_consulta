package romario.cabo.com.br.consulta_api.service.mapper.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import romario.cabo.com.br.consulta_api.model.State;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;
import romario.cabo.com.br.consulta_api.service.mapper.StateMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class StateMapperImpl implements StateMapper {

    @Value("${application.url.image}")
    private String urlImage;

    @Override
    public State toEntity(StateForm form) {
        return getState(form);
    }

    @Override
    public List<State> toEntity(List<StateForm> forms) {
        List<State> states = new ArrayList<>();

        forms.forEach(obj -> states.add(getState(obj)));

        return states;
    }

    @Override
    public State toEntity(StateDto dto) {
        return getState(dto);
    }

    @Override
    public List<State> dtoToEntity(List<StateDto> dtos) {
        List<State> states = new ArrayList<>();

        dtos.forEach(obj -> states.add(getState(obj)));

        return states;
    }

    @Override
    public StateDto toDto(State state) {
        return getState(state);
    }

    @Override
    public List<StateDto> toDto(List<State> states) {
        List<StateDto> statesDto = new ArrayList<>();

        states.forEach(obj -> statesDto.add(getState(obj)));

        return statesDto;
    }

    private State getState(StateDto dto) {
        State state = new State();
        state.setId(dto.getId());
        state.setName(dto.getName());
        state.setAcronym(dto.getAcronym());
        state.setCapital(dto.getCapital());
        state.setGentle(dto.getGentle());
        state.setTerritorialArea(dto.getTerritorialArea());
        state.setTotalCounties(dto.getTotalCounties());
        state.setTotalPopulation(dto.getTotalPopulation());
        state.setDemographicDensity(dto.getDemographicDensity());
        state.setIdh(dto.getIdh());
        state.setBorderingTerritory(dto.getBorderingTerritory());
        state.setPib(dto.getPib());
        state.setNaturalAspects(dto.getNaturalAspects());
        state.setEconomicActivities(dto.getEconomicActivities());
        state.setCuriosity(dto.getCuriosity());
        state.setRegion(dto.getRegion());

        return state;
    }

    private State getState(StateForm form) {
        State state = new State();
        state.setId(null);
        state.setName(form.getName());
        state.setAcronym(form.getAcronym());
        state.setCapital(form.getCapital());
        state.setGentle(form.getGentle());
        state.setTerritorialArea(form.getTerritorialArea());
        state.setTotalCounties(form.getTotalCounties());
        state.setTotalPopulation(form.getTotalPopulation());
        state.setDemographicDensity(form.getDemographicDensity());
        state.setIdh(form.getIdh());
        state.setBorderingTerritory(form.getBorderingTerritory());
        state.setPib(form.getPib());
        state.setNaturalAspects(form.getNaturalAspects());
        state.setEconomicActivities(form.getEconomicActivities());
        state.setCuriosity(form.getCuriosity());
        state.setRegion(form.getRegion());

        return state;
    }

    private StateDto getState(State state) {
        StateDto stateDto = new StateDto();
        stateDto.setId(state.getId());
        stateDto.setName(state.getName());
        stateDto.setAcronym(state.getAcronym());
        stateDto.setImageName(state.getImage());
        stateDto.setUrlImage(getUrl(state.getImage(), state.getId()));
        stateDto.setCapital(state.getCapital());
        stateDto.setGentle(state.getGentle());
        stateDto.setTerritorialArea(state.getTerritorialArea());
        stateDto.setTotalCounties(state.getTotalCounties());
        stateDto.setTotalPopulation(state.getTotalPopulation());
        stateDto.setDemographicDensity(state.getDemographicDensity());
        stateDto.setIdh(state.getIdh());
        stateDto.setBorderingTerritory(state.getBorderingTerritory());
        stateDto.setPib(state.getPib());
        stateDto.setNaturalAspects(state.getNaturalAspects());
        stateDto.setEconomicActivities(state.getEconomicActivities());
        stateDto.setCuriosity(state.getCuriosity());
        stateDto.setRegion(state.getRegion());

        return stateDto;
    }

    private String getUrl(String nameImage, Long id) {
        return nameImage == null ? null : urlImage + id;
    }
}