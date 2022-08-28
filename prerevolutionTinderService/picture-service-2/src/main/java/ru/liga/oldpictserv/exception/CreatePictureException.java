package ru.liga.oldpictserv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreatePictureException extends ResponseStatusException {
    public CreatePictureException() {
        super(HttpStatus.BAD_REQUEST, "Can't create picture!");
    }
}
