package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityAlreadyExistsException;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<?> handleAlreadyExists(Exception e, WebRequest request) {
        String body = "Entity already exists";
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(EntityDoesNotExistException.class)
    protected ResponseEntity<?> handleDoesNotExist(Exception e, WebRequest request) {
        String body = "Entity does not exist";
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
//
//    @ExceptionHandler(NoSuchElementException.class)
//    protected ResponseEntity<?> handleNoSuchElement(Exception e, WebRequest request) {
//        String body = "Entity does not exist";
//        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
}
