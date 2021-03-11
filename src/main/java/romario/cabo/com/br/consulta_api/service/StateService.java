package romario.cabo.com.br.consulta_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.repository.StateRepository;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;
import romario.cabo.com.br.consulta_api.service.mapper.StateMapper;
import romario.cabo.com.br.consulta_api.model.State;
import romario.cabo.com.br.consulta_api.utils.Utils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class StateService implements Crud<StateDto, StateForm, StateFilter> {

    private final StateRepository stateRepository;
    private final StateMapper stateMapper;

    @Value("${application.image.path}")
    private String folderImages;

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
        return null;
    }

    @Override
    public StateDto save(StateForm form, Long id, MultipartFile file) {
        if (id == null) {
            if (stateRepository.existsByAcronymAndName(form.getAcronym(), form.getName())) {
                throw new BadRequestException("Estado ja cadastrado!");
            }
        }

        State state;

        String fileName = file != null ? StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())).replace(" ", "_") : null;

        try {
            state = stateRepository.save(getState(form, id, fileName));
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar!");
        }

        if (file != null) {
            saveImageToDisk(file, state, fileName);
        }

        try {
            return stateMapper.toDto(state);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Não foi possível realizar o Mapper para DTO!");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<State> state = stateRepository.findById(id);

        if (!state.isPresent()) {
            throw new BadRequestException("Estado não localizado!");
        }

        try {
            stateRepository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível excluir o registro!");
        }

        if (state.get().getImage() != null) {
            deleteImageToDisk(id);
        }
    }

    @Override
    public List<StateDto> findAll(StateFilter filter) {
        return stateRepository.filterState(filter);
    }

    public byte[] getImage(Long id) {
        Optional<State> state = stateRepository.findById(id);

        if (!state.isPresent()) {
            throw new BadRequestException("ID informado não encontrado");
        }

        return Utils.getImageWithMediaType(state.get().getId(), state.get().getImage());
    }

    private State getState(StateForm form, Long id, String image) {
        try {
            State state;

            state = stateMapper.toEntity(form);
            state.setImage(image);

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

    private void saveImageToDisk(MultipartFile file, State state, String fileName) {
        try {
            String uploadDir = folderImages + state.getId();
            Utils.moveFile(uploadDir, fileName, file);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar a imagem no disco!");
        }
    }

    private void deleteImageToDisk(Long id) {
        try {
            String uploadDir = folderImages + id;
            Utils.deleteFile(uploadDir);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível deletar a imagem do disco!");
        }
    }
}
