package romario.cabo.com.br.consulta_api.service.impl;

import org.springframework.stereotype.Service;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.repository.CityRepository;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.CityFilter;
import romario.cabo.com.br.consulta_api.service.ServiceInterface;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;
import romario.cabo.com.br.consulta_api.service.form.CityForm;
import romario.cabo.com.br.consulta_api.service.mapper.CityMapper;
import romario.cabo.com.br.consulta_api.model.City;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CityServiceImpl implements ServiceInterface<CityDto, CityForm, CityFilter> {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityDto> saveAll(List<CityForm> forms) {
        List<City> cities;

        try {
            cities = cityRepository.saveAll(getCities(forms));
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar!");
        }

        try {
            return cityMapper.toDto(cities);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public CityDto save(CityForm cityForm, Long id) {
        if (existsCity(cityForm.getName(), cityForm.getIdState())) {
            throw new BadRequestException("Cidade ja cadastrada!");
        }

        City city;

        try {
            city = cityRepository.save(getCity(cityForm, id));
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar!");
        }

        try {
            return cityMapper.toDto(city);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public void delete(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new BadRequestException("Cidade não localizado!");
        }

        cityRepository.deleteById(id);
    }

    @Override
    public List<CityDto> findAll(CityFilter filter) {
        return cityRepository.filterCity(filter);
    }

    private List<City> getCities(List<CityForm> forms) {
        try {
            List<City> cities;

            cities = cityMapper.toEntity(forms);

            return cities;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para entidade!");
        }
    }

    private City getCity(CityForm cityForm, Long id) {
        try {
            City city;

            city = cityMapper.toEntity(cityForm);

            if (id != null) {
                city.setId(id);
            }

            return city;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para entidade!");
        }
    }

    private boolean existsCity(String name, Long idState) {
        City city = cityRepository.existsCity(name, idState);
        return city != null;
    }
}
