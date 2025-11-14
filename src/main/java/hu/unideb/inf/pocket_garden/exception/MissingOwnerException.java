package hu.unideb.inf.pocket_garden.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingOwnerException extends RuntimeException {
    public MissingOwnerException(String message) {
        super(message);
    }
}
