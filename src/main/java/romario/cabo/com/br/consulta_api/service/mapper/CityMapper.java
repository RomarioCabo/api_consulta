package romario.cabo.com.br.consulta_api.service.mapper;

import romario.cabo.com.br.consulta_api.model.City;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;
import romario.cabo.com.br.consulta_api.service.form.CityForm;

import java.util.List;

public interface CityMapper {

    City toEntity(CityForm form);

    List<City> toEntity(List<CityForm> forms);

    CityDto toDto(City city);

    List<CityDto> toDto(List<City> cities);
}
