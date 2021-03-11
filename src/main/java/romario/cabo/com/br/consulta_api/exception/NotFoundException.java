package romario.cabo.com.br.consulta_api.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -2869480004971965647L;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
