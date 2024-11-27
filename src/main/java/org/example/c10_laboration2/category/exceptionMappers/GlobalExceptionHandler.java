package org.example.c10_laboration2.category.exceptionMappers;

import org.example.c10_laboration2.location.exceptionMappers.LocationBadRequestExceptionMapper;
import org.example.c10_laboration2.location.exceptionMappers.LocationNotFoundExceptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ProblemDetail;

import java.net.URI;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(CategoryNotFoundExceptionMapper.class)
        public ProblemDetail handleCategoryNotFoundException(CategoryNotFoundExceptionMapper ex) {
            ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
            problemDetails.setType(URI.create("https://localhost:8080/errors/category-not-found"));
            problemDetails.setTitle("No Category Found");
            return problemDetails;
        }

        @ExceptionHandler(CategoryBadRequestExceptionMapper.class)
        public ProblemDetail handleCategoryBadRequestException(CategoryBadRequestExceptionMapper ex) {
            ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
            problemDetails.setType(URI.create("https://localhost:8080/errors/category-bad-request"));
            problemDetails.setTitle("Bad Request");
            problemDetails.setProperty("Bad request", ex.getMessage());
            return problemDetails;
        }

        @ExceptionHandler(CategoryUnAuthorizedExceptionMapper.class)
        public ProblemDetail handleCategoryNotFoundException(CategoryUnAuthorizedExceptionMapper ex) {
            ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
            problemDetails.setType(URI.create("https://localhost:8080/errors/un-authorized"));
            problemDetails.setTitle("Unauthorized");
            problemDetails.setProperty("Unauthorized", ex.getMessage());
            return problemDetails;
        }

        @ExceptionHandler(LocationNotFoundExceptionMapper.class)
        public ProblemDetail handleLocationNotFoundException(LocationNotFoundExceptionMapper ex) {
            ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
            problemDetails.setType(URI.create("https://localhost:8080/errors/location-not-found"));
            problemDetails.setTitle("No Location Found");
            return problemDetails;
        }

        @ExceptionHandler(LocationBadRequestExceptionMapper.class)
        public ProblemDetail handleLocationBadRequestException(LocationBadRequestExceptionMapper ex) {
            ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
            problemDetails.setType(URI.create("https://localhost:8080/errors/location-bad-request"));
            problemDetails.setTitle("Bad Request");
            return problemDetails;
        }

        @ExceptionHandler(Exception.class)
        public ProblemDetail handleGenericException(Exception ex) {
            ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
            problemDetails.setType(URI.create("https://localhost:8080/errors/internal-server-error"));
            problemDetails.setTitle("Internal Server Error");
            return problemDetails;
        }

    }

