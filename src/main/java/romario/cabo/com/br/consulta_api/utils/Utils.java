package romario.cabo.com.br.consulta_api.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class Utils {

    public static URI getUri(String fromHttpUrl, String queryParam, long id) {
        return ServletUriComponentsBuilder.fromHttpUrl(fromHttpUrl).
                queryParam(queryParam).buildAndExpand(id).toUri();
    }
}
