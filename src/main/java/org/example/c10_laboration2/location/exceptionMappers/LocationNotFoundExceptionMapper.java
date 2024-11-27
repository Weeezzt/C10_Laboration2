package org.example.c10_laboration2.location.exceptionMappers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class LocationNotFoundExceptionMapper extends RuntimeException{

    public LocationNotFoundExceptionMapper(int id) {
        super("Location with id " + id + " not found");
    }
}
