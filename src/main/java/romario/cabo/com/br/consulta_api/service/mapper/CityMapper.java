package romario.cabo.com.br.consulta_api.service.mapper;

import romario.cabo.com.br.consulta_api.domain.City;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;
import romario.cabo.com.br.consulta_api.service.form.CityForm;

import javax.persistence.Tuple;
import java.util.List;

public interface CityMapper {

    City toEntity(CityForm form);

    List<City> toEntity(List<CityForm> forms);

    CityDto toDto(City city);

    List<CityDto> toDto(List<City> cities);

    List<CityDto> tupleToDto(List<Tuple> tuples);
}
