package romario.cabo.com.br.consulta_api.service;

import org.springframework.data.domain.Page;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ServiceInterface<DTO, FORM, FILTER> {

    DTO save(FORM object, Long id);

    void delete(Long id);

    Page<DTO> findAll(FILTER filter, Integer page, Integer linesPerPage, String sortBy);

    default List<DTO> saveAll(List<FORM> forms) {
        return null;
    }

    default DTO update(FORM object, Long id, Long idProfile) {
        return null;
    }

    default DTO save(String object, Long id, MultipartFile imageFile) {
        return null;
    }

}
