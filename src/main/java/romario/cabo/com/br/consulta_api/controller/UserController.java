package romario.cabo.com.br.consulta_api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import romario.cabo.com.br.consulta_api.model.abstract_classes.ResponseHeaders;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.utils.Utils;
import romario.cabo.com.br.consulta_api.service.impl.UserServiceImpl;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.form.UserForm;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController extends ResponseHeaders<UserDto> {

    private final UserServiceImpl userServiceImpl;

    @Value("${application.url}")
    private String url;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @ApiOperation(httpMethod = "POST", value = "EndPoint para salvar um usuário", response = UserDto.class)
    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserForm form) {

        UserDto userDto = (userServiceImpl.save(form, null));

        URI uri = Utils.getUri(
                url + "api/v1/user",
                "id={id}",
                userDto.getId()
        );

        return ResponseEntity.created(uri).body(userDto);
    }

    @ApiOperation(httpMethod = "PUT", value = "EndPoint para alterar um usuário", response = UserDto.class)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update/{idUser}/{idProfile}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long idUser,
                                              @PathVariable Long idProfile,
                                              @RequestBody UserForm form) {

        return ResponseEntity.ok(userServiceImpl.update(form, idUser, idProfile));
    }

    @ApiOperation(httpMethod = "DELETE", value = "EndPoint para deletar um usuário")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long idUser) {
        userServiceImpl.delete(idUser);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(httpMethod = "GET", value = "EndPoint que retorna todas os usuários de acordo com os parêmtros informados", response = UserDto[].class)
    @GetMapping
    public ResponseEntity<List<UserDto>> findUsers(@ModelAttribute UserFilter filters,
                                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
                                                   @RequestParam(value = "sortBy", defaultValue = "name") String sortBy) {

        Page<UserDto> usersPage = userServiceImpl.findAll(filters, page, linesPerPage, sortBy);

        return ResponseEntity.ok().headers(responseHeaders(usersPage)).body(usersPage.getContent());
    }
}
