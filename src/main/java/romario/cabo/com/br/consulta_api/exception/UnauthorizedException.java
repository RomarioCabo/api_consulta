package romario.cabo.com.br.consulta_api.exception;

public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = -2678225697727730660L;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
