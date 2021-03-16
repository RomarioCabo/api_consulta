package romario.cabo.com.br.consulta_api.service;

import java.util.List;

public interface Crud<DTO, FORM, FILTER> {

    DTO save(FORM object, Long id);

    void delete(Long id);

    List<DTO> findAll(FILTER filter);

    default List<DTO> saveAll(List<FORM> forms) {
        return null;
    }
}
