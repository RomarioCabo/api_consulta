package romario.cabo.com.br.consulta_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.utils.Utils;
import romario.cabo.com.br.consulta_api.service.UserService;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.UserForm;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@Api(value = "API Consulta")
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @Value("${application.url}")
    private String url;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(httpMethod = "POST", value = "EndPoint para salvar um usuário", response = UserDto.class)
    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserForm form) {

        UserDto userDto = (userService.save(form, null));

        URI uri = Utils.getUri(
                url+"api/v1/user",
                "id={id}",
                userDto.getId()
        );

        return ResponseEntity.created(uri).body(userDto);
    }

    @ApiOperation(httpMethod = "PUT", value = "EndPoint para alterar um usuário", response = UserDto.class)
    @PutMapping("/update/{idUser}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long idUser,
                                              @RequestBody UserForm form) {

        return ResponseEntity.ok(userService.update(form, idUser));
    }

    @ApiOperation(httpMethod = "DELETE", value = "EndPoint para deletar um usuário")
    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long idUser) {
        userService.delete(idUser);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(httpMethod = "GET", value = "EndPoint que retorna todas os usuários de acordo com os parêmtros informado", response = UserDto[].class)
    @GetMapping
    public ResponseEntity<List<UserDto>> findUser(@ModelAttribute UserFilter filter) {

        return ResponseEntity.ok(userService.findAll(filter));
    }
}
