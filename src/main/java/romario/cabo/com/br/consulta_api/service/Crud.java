package romario.cabo.com.br.consulta_api.service;

import java.util.List;

public interface Crud<DTO, FORM, FILTER> {

    DTO save(FORM object, Long id);

    void delete(Long id);

    List<DTO> findAll(FILTER filter);

    default List<DTO> saveAll(List<FORM> forms) {
        return null;
    }
<<<<<<< HEAD
=======

    default DTO save(FORM object, Long id, MultipartFile file) {
        return null;
    }

    default DTO save(FORM object) {
        return null;
    }

    default DTO update(FORM object, Long id){return null;}
>>>>>>> 07708d46e29a06df17c3276566a9832f47145516
}
