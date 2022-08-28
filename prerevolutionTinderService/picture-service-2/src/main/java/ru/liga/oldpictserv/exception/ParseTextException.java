package ru.liga.oldpictserv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ParseTextException extends ResponseStatusException {
    public ParseTextException() {
        super(HttpStatus.BAD_REQUEST, "Can't parse text!");
    }
}

