package rr.labs.elastic.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import rr.labs.elastic.book.api.BookApi;
import rr.labs.elastic.book.api.BookFilterResource;
import rr.labs.elastic.book.api.BookResource;
import rr.labs.elastic.book.domain.Book;
import rr.labs.elastic.book.domain.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController implements BookApi {

    @Autowired
    private BookService bookService;

    @Override
    public ResponseEntity<Void> create(final @Valid BookResource request) {
        bookService.save(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> update(@Valid BookResource request) {
        bookService.update(request.toDomain());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Book>> find(final BookFilterResource filter) {
        final List<Book> response = this.bookService.findByFilter(filter.toDomain());
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> remove(final String isbn) {
        this.bookService.delete(Book.builder().isbn(isbn).build());
        return ResponseEntity.ok().build();
    }
}
