package eu.frezilla.sandbox.microservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShelvingNotFoundException extends RuntimeException {

    public ShelvingNotFoundException(String string) {
        super(string);
    }
    
}
