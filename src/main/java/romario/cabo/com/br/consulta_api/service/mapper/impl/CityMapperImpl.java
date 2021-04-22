package romario.cabo.com.br.consulta_api.service.mapper.impl;

import org.springframework.stereotype.Component;
import romario.cabo.com.br.consulta_api.domain.City;
import romario.cabo.com.br.consulta_api.domain.State;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;
import romario.cabo.com.br.consulta_api.service.form.CityForm;
import romario.cabo.com.br.consulta_api.service.mapper.CityMapper;
import romario.cabo.com.br.consulta_api.service.mapper.StateMapper;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapperImpl implements CityMapper {

    private final StateMapper stateMapper;

    public CityMapperImpl(StateMapper stateMapper) {
        this.stateMapper = stateMapper;
    }

    @Override
    public City toEntity(CityForm form) {
        return getCity(form);
    }

    @Override
    public List<City> toEntity(List<CityForm> forms) {
        List<City> cities = new ArrayList<>();

        forms.forEach(obj -> cities.add(getCity(obj)));

        return cities;
    }

    @Override
    public CityDto toDto(City city) {
        return getCity(city);
    }

    @Override
    public List<CityDto> toDto(List<City> cities) {
        List<CityDto> citiesDto = new ArrayList<>();

        cities.forEach(obj -> citiesDto.add(getCity(obj)));

        return citiesDto;
    }

    @Override
    public List<CityDto> tupleToDto(List<Tuple> tuple) {
        List<CityDto> citiesDto = new ArrayList<>();

        tuple.forEach(obj -> citiesDto.add(getCity(obj)));

        return citiesDto;
    }

    private CityDto getCity(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        cityDto.setState(stateMapper.toDto(city.getState()));

        return cityDto;
    }

    private CityDto getCity(Tuple tuple) {
        CityDto cityDto = new CityDto();
        cityDto.setId((Long) tuple.get(0));
        cityDto.setName((String) tuple.get(1));
        cityDto.setState(stateMapper.toDto((State) tuple.get(2)));

        return cityDto;
    }

    private City getCity(CityForm form) {
        City city = new City();
        city.setId(null);
        city.setName(form.getName());
        city.setState(getState(form));

        return city;
    }

    private State getState(CityForm form) {
        State state = new State();
        state.setId(form.getIdState());
        state.setName(form.getName());
        state.setAcronym(null);

        return state;
    }
}