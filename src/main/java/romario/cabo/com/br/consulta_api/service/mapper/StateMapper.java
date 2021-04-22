package romario.cabo.com.br.consulta_api.service.mapper;

import romario.cabo.com.br.consulta_api.domain.State;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;

import javax.persistence.Tuple;
import java.util.List;

public interface StateMapper {

    State toEntity(StateForm form);

    List<State> toEntity(List<StateForm> forms);

    State toEntity(StateDto dto);

    List<State> dtoToEntity(List<StateDto> dtos);

    StateDto toDto(State state);

    List<StateDto> toDto(List<State> states);

    List<StateDto> tupleToDto(List<Tuple> tuples);
}
