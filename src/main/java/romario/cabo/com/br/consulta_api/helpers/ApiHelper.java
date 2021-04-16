package romario.cabo.com.br.consulta_api.helpers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class ApiHelper<DTO> {

    public HttpHeaders responseHeaders(Page<DTO> listPages) {
        if (listPages.isEmpty()) {
            return null;
        }

        int totalPages = listPages.getTotalPages();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("totalPages", Integer.toString(totalPages));
        responseHeaders.set("access-control-expose-headers", "totalPages");
        responseHeaders.set("totalElements", Long.toString(listPages.getTotalElements()));
        responseHeaders.set("access-control-expose-headers", "totalElements");

        return responseHeaders;
    }

    public URI getUri(String fromHttpUrl, String queryParam, long id) {
        return ServletUriComponentsBuilder.fromHttpUrl(fromHttpUrl).
                queryParam(queryParam).buildAndExpand(id).toUri();
    }
}
