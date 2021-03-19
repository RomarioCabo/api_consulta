package romario.cabo.com.br.consulta_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.StateService;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;
import romario.cabo.com.br.consulta_api.utils.Utils;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@Api(value = "API Consulta")
@RestController
@RequestMapping(value = "/api/v1/state")
public class StateController {

    private final StateService stateService;

    @Value("${application.url}")
    private String url;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @ApiOperation(httpMethod = "POST", value = "EndPoint para salvar um estado", response = StateDto.class)
    @PostMapping("/save")
    public ResponseEntity<StateDto> saveStateWithImage(@RequestBody StateForm form) {

        StateDto stateDto = stateService.save(form, null);

        URI uri = Utils.getUri(
                url + "api/v1/state",
                "id={id}",
                stateDto.getId()
        );

        return ResponseEntity.created(uri).body(stateDto);
    }

    @ApiOperation(httpMethod = "PUT", value = "EndPoint para alterar um estado", response = StateDto.class)
    @PutMapping("/update")
    public ResponseEntity<StateDto> updateState(@RequestParam Long idState,
                                                @RequestBody StateForm form) {

        return ResponseEntity.ok(stateService.save(form, idState));
    }

    @ApiOperation(httpMethod = "DELETE", value = "EndPoint para deletar um estado")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        stateService.delete(id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(httpMethod = "GET", value = "EndPoint que retorna todas os estados de acordo com os parêmtros informado", response = StateDto[].class)
    @GetMapping
    public ResponseEntity<List<StateDto>> findState(@ModelAttribute StateFilter filters) {

        return ResponseEntity.ok(stateService.findAll(filters));
    }

    @ApiOperation(httpMethod = "GET", value = "EndPoint para obter a imagem")
    @GetMapping(
            value = "/getImage/{id}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public ResponseEntity<byte[]> getImageWithMediaType(@PathVariable Long id) {

        return ResponseEntity.ok(stateService.getImage(id));
    }
}
