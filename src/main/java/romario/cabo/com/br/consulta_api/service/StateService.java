package romario.cabo.com.br.consulta_api.service;

import org.springframework.stereotype.Service;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.repository.StateRepository;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;
import romario.cabo.com.br.consulta_api.service.mapper.StateMapper;
import romario.cabo.com.br.consulta_api.model.State;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StateService implements Crud<StateDto, StateForm, StateFilter> {

    private final StateRepository stateRepository;
    private final StateMapper stateMapper;

    public StateService(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    @Override
    public List<StateDto> saveAll(List<StateForm> forms) {
        List<State> states;

        try {
            states = stateRepository.saveAll(getStates(forms));
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar!");
        }

        try {
            return stateMapper.toDto(states);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public StateDto save(StateForm form, Long id) {
        if (stateRepository.existsByAcronymAndName(form.getAcronym(), form.getName())) {
            throw new BadRequestException("Estado ja cadastrado!");
        }

        State state;

        try {
            state = stateRepository.save(getState(form, id));
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar!");
        }

        try {
            return stateMapper.toDto(state);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public void delete(Long id) {
        if (!stateRepository.existsById(id)) {
            throw new BadRequestException("Estado não localizado!");
        }

        stateRepository.existsById(id);
    }

    @Override
    public List<StateDto> findAll(StateFilter filter) {
        return stateRepository.filterState(filter);
    }

    private State getState(StateForm form, Long id) {
        try {
            State state;

            state = stateMapper.toEntity(form);

            if (id != null) {
                state.setId(id);
            }

            return state;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para entidade!");
        }
    }

    private List<State> getStates(List<StateForm> forms) {
        try {
            List<State> states;

            states = stateMapper.toEntity(forms);

            return states;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível realizar o Mapper para entidade!");
        }
    }
}
