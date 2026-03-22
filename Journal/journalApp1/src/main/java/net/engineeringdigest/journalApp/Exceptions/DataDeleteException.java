package net.engineeringdigest.journalApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataDeleteException extends RuntimeException {
    public DataDeleteException(String message) {
        super(message);
    }
}

