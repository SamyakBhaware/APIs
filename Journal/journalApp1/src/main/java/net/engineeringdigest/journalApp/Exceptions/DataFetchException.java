package net.engineeringdigest.journalApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataFetchException extends RuntimeException {
    public DataFetchException(String message) {
        super(message);
    }
}

