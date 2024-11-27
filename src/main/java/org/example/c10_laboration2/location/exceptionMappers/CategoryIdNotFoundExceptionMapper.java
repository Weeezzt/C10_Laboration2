package org.example.c10_laboration2.category.exceptionMappers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public class CategoryNotFoundExceptionMapper extends RuntimeException {

        private String name;

        public CategoryNotFoundExceptionMapper(String name) {
            super("Category with name " + name + " not found");
        }
    }





