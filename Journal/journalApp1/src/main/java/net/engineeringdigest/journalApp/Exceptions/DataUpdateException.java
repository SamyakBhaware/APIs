package net.engineeringdigest.journalApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataUpdateException extends RuntimeException {
    public DataUpdateException(String message) {
        super(message);
    }
}

