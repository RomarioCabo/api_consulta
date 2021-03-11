package romario.cabo.com.br.consulta_api.contrller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.StateService;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.form.StateForm;

import java.util.List;

@CrossOrigin(origins = "*")
@Api(value = "API Consulta")
@RestController
@RequestMapping(value = "/api/v1/state")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @ApiOperation(httpMethod = "POST", value = "EndPoint para salvar vários estados", response = StateDto[].class)
    @PostMapping("/saveAll")
    public ResponseEntity<List<StateDto>> saveAllStates(@RequestBody List<StateForm> forms) {

        return ResponseEntity.created(null).body(stateService.saveAll(forms));

    }

    @ApiOperation(httpMethod = "POST", value = "EndPoint para salvar um estado", response = StateDto.class)
    @PostMapping("/save")
    public ResponseEntity<StateDto> saveState(@RequestBody StateForm form) {

        return ResponseEntity.created(null).body(stateService.save(form, null));

    }

    @ApiOperation(httpMethod = "PUT", value = "EndPoint para alterar um estado", response = StateDto.class)
    @PutMapping("/update/{idState}")
    public ResponseEntity<StateDto> updateState(@PathVariable Long idState,
                                                @RequestBody StateForm form) {

        return ResponseEntity.ok(stateService.save(form, idState));
    }

    @ApiOperation(httpMethod = "DELETE", value = "EndPoint para deletar um estado")
    @PutMapping("/delete/{idState}")
    public ResponseEntity<Void> deleteState(@PathVariable Long idState) {
        stateService.delete(idState);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(httpMethod = "GET", value = "EndPoint que retorna todas os estados de acordo com os parêmtros informado", response = StateDto[].class)
    @GetMapping
    public ResponseEntity<List<StateDto>> findState(@ModelAttribute StateFilter filters) {

        return ResponseEntity.ok(stateService.findAll(filters));
    }
}
