package romario.cabo.com.br.consulta_api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.impl.StateServiceImpl;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;
import romario.cabo.com.br.consulta_api.utils.Utils;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/state")
public class StateController {

    private final StateServiceImpl stateServiceImpl;

    @Value("${application.url}")
    private String url;

    public StateController(StateServiceImpl stateServiceImpl) {
        this.stateServiceImpl = stateServiceImpl;
    }

    @ApiOperation(httpMethod = "POST", value = "EndPoint para salvar um estado", response = StateDto.class)
    @PostMapping("/save")
    public ResponseEntity<StateDto> saveState(@RequestBody StateForm form) {

        StateDto stateDto = stateServiceImpl.save(form, null);

        URI uri = Utils.getUri(
                url + "api/v1/state",
                "id={id}",
                stateDto.getId()
        );

        return ResponseEntity.created(uri).body(stateDto);
    }

    @ApiOperation(httpMethod = "PUT", value = "EndPoint para alterar um estado", response = StateDto.class)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update/{idState}")
    public ResponseEntity<StateDto> updateState(@PathVariable Long idState,
                                                @RequestBody StateForm form) {

        return ResponseEntity.ok(stateServiceImpl.save(form, idState));
    }

    @ApiOperation(httpMethod = "DELETE", value = "EndPoint para deletar um estado")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        stateServiceImpl.delete(id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(httpMethod = "GET", value = "EndPoint que retorna todas os estados de acordo com os parêmtros informados", response = StateDto[].class)
    @GetMapping
    public ResponseEntity<List<StateDto>> findStates(@ModelAttribute StateFilter filters,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
                                                     @RequestParam(value = "sortBy", defaultValue = "name") String sortBy) {

        Page<StateDto> statesPage = stateServiceImpl.findAll(filters, page, linesPerPage, sortBy);

        return ResponseEntity.ok().headers(stateServiceImpl.responseHeaders(statesPage)).body(statesPage.getContent());
    }

    @ApiOperation(httpMethod = "GET", value = "EndPoint para obter a imagem")
    @GetMapping(
            value = "/getImage/{id}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public ResponseEntity<byte[]> getImageWithMediaType(@PathVariable Long id) {

        return ResponseEntity.ok(stateServiceImpl.getImage(id));
    }
}
