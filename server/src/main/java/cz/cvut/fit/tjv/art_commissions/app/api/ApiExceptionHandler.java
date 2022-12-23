package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityDoesNotExistException.class)
    protected ResponseEntity<ApiErrorResponse> handleDoesNotExist(Exception ex, WebRequest webRequest) {
        String errorBody = "The requested entity was not found";
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND, errorBody, webRequest, ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CommissionException.class, ArtistException.class, CustomerException.class})
    protected ResponseEntity<ApiErrorResponse> handleInvalidData(Exception ex, WebRequest webRequest) {
        String errorBody = "Received invalid data while creating or updating an entity";
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorBody, webRequest, ex), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        String errorBody = "The received data does not match expected data types";
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorBody, webRequest, ex), HttpStatus.BAD_REQUEST);
    }
}
