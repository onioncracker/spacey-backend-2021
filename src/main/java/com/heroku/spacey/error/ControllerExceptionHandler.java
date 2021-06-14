package com.heroku.spacey.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.webjars.NotFoundException;

import java.util.Date;


@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
//        ErrorMessage message = new ErrorMessage(
//                HttpStatus.BAD_REQUEST.value(),
//                new Date(),
//                ex.getMessage(),
//                request.getDescription(false));
//
//        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(UserNotActivatedException.class)
//    public ResponseEntity<ErrorMessage> userNotActivatedException(UserNotActivatedException ex, WebRequest request) {
//        ErrorMessage message = new ErrorMessage(
//            HttpStatus.FORBIDDEN.value(),
//            new Date(),
//            ex.getMessage(),
//            request.getDescription(false));
//
//        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
//        ErrorMessage message = new ErrorMessage(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                new Date(),
//                ex.getMessage(),
//                request.getDescription(false));
//
//        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException ex, WebRequest request) {
//        ErrorMessage message = new ErrorMessage(
//                HttpStatus.NOT_FOUND.value(),
//                new Date(),
//                ex.getMessage(),
//                request.getDescription(false));
//
//        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ErrorMessage> badCredentialsException(BadCredentialsException ex, WebRequest request) {
//        ErrorMessage message = new ErrorMessage(
//            HttpStatus.UNAUTHORIZED.value(),
//            new Date(),
//            ex.getMessage(),
//            request.getDescription(false));
//        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
//    }
}