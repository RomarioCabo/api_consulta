package romario.cabo.com.br.consulta_api.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public class ResponseHeaders<DTO> {

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

}
