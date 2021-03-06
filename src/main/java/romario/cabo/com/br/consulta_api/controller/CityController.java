package romario.cabo.com.br.consulta_api.controller;

import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import romario.cabo.com.br.consulta_api.helpers.ApiHelper;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.CityFilter;
import romario.cabo.com.br.consulta_api.service.impl.CityServiceImpl;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;
import romario.cabo.com.br.consulta_api.service.form.CityForm;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/city")
public class CityController extends ApiHelper<CityDto> {

  private final CityServiceImpl cityServiceImpl;

  @Value("${application.url}")
  private String url;

  public CityController(CityServiceImpl cityServiceImpl) {
    this.cityServiceImpl = cityServiceImpl;
  }

  @ApiOperation(httpMethod = "POST", value = "EndPoint para salvar uma lista de cidades", response = CityDto[].class)
  @PostMapping("/saveListCities")
  public ResponseEntity<List<CityDto>> saveListCities(@RequestBody List<CityForm> forms) {

    return ResponseEntity.ok(cityServiceImpl.saveAll(forms));
  }

  @ApiOperation(httpMethod = "POST", value = "EndPoint para salvar uma cidade", response = CityDto.class)
  @PostMapping("/save")
  public ResponseEntity<CityDto> saveCity(@RequestBody CityForm form) {
    CityDto cityDto = cityServiceImpl.save(form, null);

    return ResponseEntity.created(getUri(url + "api/v1/city", "idCity={idCity}", cityDto.getId()))
        .body(cityDto);
  }

  @ApiOperation(httpMethod = "PUT", value = "EndPoint para alterar uma cidade", response = CityDto.class)
  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping("/update/{idCity}")
  public ResponseEntity<CityDto> updateCity(@PathVariable Long idCity,
      @RequestBody CityForm form) {

    return ResponseEntity.ok(cityServiceImpl.save(form, idCity));
  }

  @ApiOperation(httpMethod = "DELETE", value = "EndPoint para deletar uma cidade")
  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/delete/{idCity}")
  public ResponseEntity<Void> deleteCity(@PathVariable Long idCity) {
    cityServiceImpl.delete(idCity);

    return ResponseEntity.ok().build();
  }

  @ApiOperation(httpMethod = "GET", value = "EndPoint que retorna todas as cidades de acordo com os par??mtros informados", response = CityDto[].class)
  @GetMapping
  public ResponseEntity<List<CityDto>> findCities(@ModelAttribute CityFilter filters,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
      @RequestParam(value = "sortBy", defaultValue = "name") String sortBy) {

    Page<CityDto> citiesPage = cityServiceImpl.findAll(filters, page, linesPerPage, sortBy);

    List<CityDto> emptyArray = new ArrayList<>();

    return ResponseEntity.ok().headers(responseHeaders(citiesPage))
        .body(citiesPage == null ? emptyArray : citiesPage.getContent());
  }
}
