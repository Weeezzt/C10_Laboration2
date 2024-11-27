package org.example.c10_laboration2.location.exceptionMappers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class LocationNotFoundWithCategory extends RuntimeException{

    public LocationNotFoundWithCategory(String category){
        super("Location with category " + category + " not found");
    }
}
