package romario.cabo.com.br.consulta_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import romario.cabo.com.br.consulta_api.service.AuthenticateService;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.AuthenticateForm;

@CrossOrigin(origins = "*")
@Api(value = "API Consulta")
@RestController
@RequestMapping(value = "/api/v1/authenticate")
public class AuthenticateController {

    private final AuthenticateService authService;

    public AuthenticateController(AuthenticateService authService) {
        this.authService = authService;
    }

    @ApiOperation(httpMethod = "POST", value = "EndPoint para autenticar um usuário", response = UserDto.class)
    @PostMapping
    public ResponseEntity<UserDto> authenticateUser(@RequestBody AuthenticateForm form) {

        return ResponseEntity.ok(authService.authenticateUser(form));

    }
}
