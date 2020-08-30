package rr.labs.elastic.book.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rr.labs.elastic.book.domain.Book;
import rr.labs.elastic.book.domain.repository.BookRepository;
import rr.labs.elastic.book.domain.service.BookService;
import rr.labs.elastic.infrastructure.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
class BookServiceImpl implements BookService {

    @Autowired private BookRepository repository;

    public void save(final Book book) {
        this.repository.save(book);
    }

    public void update(final Book book) {
        final Optional<Book> bookPersisted = this.repository.findById(book.getIsbn());
        bookPersisted.orElseThrow(() -> new BookNotFoundException("Book not found"));
        this.repository.save(this.fillBookChange(book, bookPersisted));
    }

    @NotNull
    private Book fillBookChange(Book book, Optional<Book> bookPersisted) {
        final Book bookChange = bookPersisted.get();
        bookChange.setDescription(StringUtils.firstNonBlank(book.getDescription(), bookChange.getDescription()));
        bookChange.setName(StringUtils.firstNonBlank(book.getName(), bookChange.getName()));
        bookChange.setPrice(ObjectUtils.firstNonNull(book.getPrice(), bookChange.getPrice()));
        return bookChange;
    }

    public void delete(final Book book) {
        this.repository.delete(book);
    }

    @Override
    public List<Book> findByFilter(final Book filter) {
        return this.repository.findByFilter(filter.getName(), filter.getDescription(), filter.getIsbn());
    }

    @Override
    public Optional<Book> findById(final String isbn) {
        return this.repository.findById(isbn);
    }
}
