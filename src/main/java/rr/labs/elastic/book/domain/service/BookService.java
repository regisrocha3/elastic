package rr.labs.elastic.book.domain.service;

import rr.labs.elastic.book.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void save(Book book);
    void update(Book book);
    void delete(Book book);
    List<Book> findByFilter(Book filter);
    Optional<Book> findById(final String isbn);
}
