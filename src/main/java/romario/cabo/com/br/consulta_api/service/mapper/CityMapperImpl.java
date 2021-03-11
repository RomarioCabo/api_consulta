package romario.cabo.com.br.consulta_api.service.mapper;

import org.springframework.stereotype.Component;
import romario.cabo.com.br.consulta_api.model.City;
import romario.cabo.com.br.consulta_api.model.State;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;
import romario.cabo.com.br.consulta_api.service.form.CityForm;

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

    private CityDto getCity(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        cityDto.setState(stateMapper.toDto(city.getState()));

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
