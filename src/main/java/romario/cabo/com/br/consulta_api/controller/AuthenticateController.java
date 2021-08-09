package romario.cabo.com.br.consulta_api.controller;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import romario.cabo.com.br.consulta_api.service.AuthenticateService;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.AuthenticateForm;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1")
public class AuthenticateController {

  private final AuthenticateService authenticate;

  public AuthenticateController(AuthenticateService authenticate) {
    this.authenticate = authenticate;
  }

  @ApiOperation(httpMethod = "POST", value = "EndPoint para validar as credenciais de um usuário", response = UserDto.class)
  @PostMapping("/authenticate")
  public ResponseEntity<UserDto> authenticate(@RequestBody AuthenticateForm form) {

    return ResponseEntity.ok(authenticate.authenticate(form));
  }
}
