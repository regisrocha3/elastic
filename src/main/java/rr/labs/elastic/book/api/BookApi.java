package rr.labs.elastic.book.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rr.labs.elastic.book.domain.Book;

import javax.validation.Valid;
import java.util.List;

@OpenAPIDefinition
@RequestMapping(value = "/api/v1/book", produces = MediaType.APPLICATION_JSON_VALUE)
public interface BookApi {

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody BookResource request);

    @PutMapping
    ResponseEntity<Void> update(@Valid @RequestBody BookResource request);

    @GetMapping
    ResponseEntity<List<Book>> find(BookFilterResource request);

    @DeleteMapping("/{isbn}")
    ResponseEntity<Void> remove(@PathVariable("isbn") String isbn);
}
