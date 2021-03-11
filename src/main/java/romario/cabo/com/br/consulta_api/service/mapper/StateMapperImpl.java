package romario.cabo.com.br.consulta_api.service.mapper;

import org.springframework.stereotype.Component;
import romario.cabo.com.br.consulta_api.model.State;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;
import romario.cabo.com.br.consulta_api.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component
public class StateMapperImpl implements StateMapper{

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

        return state;
    }

    private State getState(StateForm form) {
        State state = new State();
        state.setId(null);
        state.setName(form.getName());
        state.setAcronym(form.getAcronym());

        return state;
    }

    private StateDto getState(State state) {
        StateDto stateDto = new StateDto();
        stateDto.setId(state.getId());
        stateDto.setName(state.getName());
        stateDto.setAcronym(state.getAcronym());
        stateDto.setImageBase64(Utils.getImageWithMediaType(state.getId(), state.getImage()));

        return stateDto;
    }
}
