package romario.cabo.com.br.consulta_api.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ServiceInterface<DTO, FORM, FILTER> {

    DTO save(FORM object, Long id);

    void delete(Long... params);

    Page<DTO> findAll(FILTER filter, Integer page, Integer linesPerPage, String sortBy);

    default List<DTO> saveAll(List<FORM> forms) {
        return null;
    }

    default DTO update(FORM object, Long id, Long idProfile) {
        return null;
    }

}
