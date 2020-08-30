package rr.labs.elastic.infrastructure.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rr.labs.elastic.infrastructure.exception.BookNotFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Response> handleBookNotFound(BookNotFoundException e) {
        final Response response = Response.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now().toString()).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
