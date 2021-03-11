package romario.cabo.com.br.consulta_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity BadRequestException(BadRequestException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity NotFoundException(NotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity UnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }
}
