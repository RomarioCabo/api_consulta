package romario.cabo.com.br.consulta_api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
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
import romario.cabo.com.br.consulta_api.domain.State;
import romario.cabo.com.br.consulta_api.helpers.Utils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Transactional
public class StateServiceImpl implements ServiceInterface<StateDto, StateForm, StateFilter> {

  private final StateRepository stateRepository;
  private final StateMapper stateMapper;

  @Value("${application.image.path}")
  private String folderImages;

  public StateServiceImpl(StateRepository stateRepository, StateMapper stateMapper) {
    this.stateRepository = stateRepository;
    this.stateMapper = stateMapper;
  }

  @Override
  public StateDto save(StateForm object, Long id) {
    return null;
  }

  @Override
  public StateDto save(String jsonObject, Long id, MultipartFile imageFile) {
    StateForm form = stringToObject(jsonObject);

    stateExists(id, form);

    State stateOptional = stateExists(id);

    State state;

    String fileName = getFileName(imageFile, stateOptional.getImage());

    state = saveState(id, form, fileName);

    saveImageToDisk(imageFile, state, fileName);

    return mapperState(state);
  }

  @Override
  public void delete(Long idState) {

    State state = stateExists(idState, "Estado não localizado em nossa base de dados!");

    deleteState(idState);

    deleteImageToDisk(idState, state.getImage());

  }

  @Override
  public Page<StateDto> findAll(StateFilter filter, Integer page, Integer linesPerPage,
      String sortBy) {
    try {
      Pageable pageable = PageRequest.of(page, linesPerPage, Sort.by(sortBy));

      Page<StateDto> statesPage = stateRepository.filterState(filter, pageable);

      if (statesPage == null || statesPage.isEmpty()) {
        return null;
      }

      return statesPage;
    } catch (Exception e) {
      throw new InternalServerErrorException("Não foi possível retornar os dados!");
    }
  }

  public byte[] getImage(Long id) {
    State state = stateExists(id, "ID informado não encontrado");

    return Utils.getImageWithMediaType(state.getId(), state.getImage());
  }

  private void saveImageToDisk(MultipartFile file, State state, String fileName) {
    try {
      if (file != null && !file.isEmpty()) {
        String uploadDir = folderImages + state.getId();
        Utils.moveFile(uploadDir, fileName, file);
      }
    } catch (Exception e) {
      throw new BadRequestException("Não foi possível salvar a imagem no disco!");
    }
  }

  private void deleteImageToDisk(Long id, String image) {
    try {
      if (image != null) {
        String uploadDir = folderImages + id;
        Utils.deleteFile(uploadDir);
      }
    } catch (Exception e) {
      throw new BadRequestException("Não foi possível deletar a imagem do disco!");
    }
  }

  private String getFileName(MultipartFile file, String imageName) {
    if (file != null && !file.isEmpty()) {
      String rawString = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

      byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);

      return new String(bytes, StandardCharsets.UTF_8);
    }

    return imageName;
  }

  private StateForm stringToObject(String objectJson) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(objectJson, StateForm.class);
    } catch (Exception e) {
      throw new BadRequestException("Não foi possível converter a string em um objeto!");
    }
  }

  private State stateExists(Long id) {
    Optional<State> stateOptional = Optional.empty();

    if (id != null) {
      stateOptional = stateRepository.findById(id);

      if (!stateOptional.isPresent()) {
        throw new BadRequestException("Estado informado não encotrado!");
      }
    }

    return stateOptional.get();
  }

  private void stateExists(Long id, StateForm form) {
    if (id == null) {
      if (stateRepository.existsByAcronymAndName(form.getAcronym(), form.getName())) {
        throw new BadRequestException("Estado ja cadastrado!");
      }
    }
  }

  private State saveState(Long id, StateForm form, String fileName) {
    State state;

    try {
      state = stateRepository.save(getState(form, id, fileName));
    } catch (Exception e) {
      throw new BadRequestException("Não foi possível salvar!");
    }

    return state;
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

  private StateDto mapperState(State state) {
    try {
      return stateMapper.toDto(state);
    } catch (Exception e) {
      throw new BadRequestException("Não foi possível realizar o Mapper para DTO!");
    }
  }

  private State stateExists(Long idState, String messageError) {
    return stateRepository
        .findById(idState).orElseThrow(
            () -> new BadRequestException(messageError));
  }

  private void deleteState(Long idState) {
    try {
      stateRepository.deleteById(idState);
    } catch (Exception e) {
      throw new InternalServerErrorException("Não foi possível excluir o registro!");
    }
  }
}
