package romario.cabo.com.br.consulta_api.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.exception.InternalServerErrorException;
import romario.cabo.com.br.consulta_api.repository.StateRepository;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.ServiceInterface;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;
import romario.cabo.com.br.consulta_api.service.mapper.StateMapper;
import romario.cabo.com.br.consulta_api.model.State;
import romario.cabo.com.br.consulta_api.utils.BASE64DecodedMultipartFile;
import romario.cabo.com.br.consulta_api.model.abstract_classes.ResponseHeaders;
import romario.cabo.com.br.consulta_api.utils.Utils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class StateServiceImpl extends ResponseHeaders<StateDto> implements ServiceInterface<StateDto, StateForm, StateFilter> {

    private final StateRepository stateRepository;
    private final StateMapper stateMapper;

    @Value("${application.image.path}")
    private String folderImages;

    public StateServiceImpl(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    @Override
    public StateDto save(StateForm form, Long id) {
        if (id == null) {
            if (stateRepository.existsByAcronymAndName(form.getAcronym(), form.getName())) {
                throw new BadRequestException("Estado ja cadastrado!");
            }
        }

        MultipartFile file = BASE64DecodedMultipartFile.base64ToMultipart(form.getFileInBase64());

        State state;

        String fileName = getFileName(file);

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
    public Page<StateDto> findAll(StateFilter filter, Integer page, Integer linesPerPage, String sortBy) {
        try {
            Pageable pageable = PageRequest.of(page, linesPerPage, Sort.by(sortBy));

            Page<StateDto> statesPage = stateRepository.filterState(filter, pageable);

            if (statesPage.isEmpty()) {
                return null;
            }

            return statesPage;
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível retornar os dados! \nError: " + e.getMessage());
        }
    }

    public byte[] getImage(Long id) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("ID informado não encontrado"));

        return Utils.getImageWithMediaType(state.getId(), state.getImage());
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

    private String getFileName(MultipartFile file) {
        if (file == null) {
            return null;
        } else {
            String rawString = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);

            return new String(bytes, StandardCharsets.UTF_8);
        }
    }
}
