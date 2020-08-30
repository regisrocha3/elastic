package rr.labs.elastic.book;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import rr.labs.elastic.book.domain.Book;
import rr.labs.elastic.book.domain.service.BookService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookIT {

    @Autowired
    private BookService bookService;

    @Autowired
    private ElasticsearchRepository<Book, String> elasticRepository;

    @Container
    private static ElasticsearchContainer elasticsearchContainer = new rr.labs.elastic.ElasticsearchContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @Test
    void testCreateBook() {
        this.bookService.save(Book.builder()
                .isbn("11")
                .price(new BigDecimal("20.87"))
                .name("the infinite game")
                .description("desc")
                .build());

        final Optional<Book> bookFound = this.elasticRepository.findById("11");
        this.assertBookInfo(bookFound.get(), "the infinite game", "desc", "20.87");
    }

    @Test
    void testUpdateDescriptionBook() {
        this.bookService.save(Book.builder()
                .isbn("11000").price(new BigDecimal("200.99")).name("book")
                .description("desc").build());

        final Optional<Book> bookFound = this.elasticRepository.findById("11000");
        this.assertBookInfo(bookFound.get(), "book", "desc", "200.99");

        this.bookService.update(Book.builder().isbn("11000").description("description").build());
        final Optional<Book> updated = this.bookService.findById("11000");
        this.assertBookInfo(updated.get(), "book", "description", "200.99");
    }

    @Test
    void testUpdatePriceBook() {
        this.bookService.save(Book.builder()
                .isbn("11001").price(new BigDecimal("200.99")).name("book")
                .description("desc").build());

        final Optional<Book> bookFound = this.elasticRepository.findById("11001");
        this.assertBookInfo(bookFound.get(), "book", "desc", "200.99");

        this.bookService.update(Book.builder().isbn("11001").price(new BigDecimal("30.98")).build());
        final Optional<Book> updated = this.bookService.findById("11001");
        this.assertBookInfo(updated.get(), "book", "desc", "30.98");
    }

    @Test
    void testUpdateNameBook() {
        this.bookService.save(Book.builder()
                .isbn("11002").price(new BigDecimal("200.99")).name("book")
                .description("desc").build());

        final Optional<Book> bookFound = this.elasticRepository.findById("11002");
        this.assertBookInfo(bookFound.get(), "book", "desc", "200.99");

        this.bookService.update(Book.builder().isbn("11002").price(new BigDecimal("30.98")).build());
        final Optional<Book> updated = this.bookService.findById("11002");
        this.assertBookInfo(updated.get(), "book", "desc", "30.98");
    }

    @Test
    void testUpdateAllFieldsBook() {
        this.bookService.save(Book.builder()
                .isbn("11003").price(new BigDecimal("200.99")).name("book")
                .description("desc").build());

        final Optional<Book> bookFound = this.elasticRepository.findById("11003");
        this.assertBookInfo(bookFound.get(), "book", "desc", "200.99");

        this.bookService.update(Book.builder().isbn("11003")
                .name("book new")
                .description("description book new")
                .price(new BigDecimal("11.98")).build());

        final Optional<Book> updated = this.bookService.findById("11003");
        this.assertBookInfo(updated.get(), "book new", "description book new", "11.98");
    }

    @Test
    void testFindBookById() {
        final String isbn = UUID.randomUUID().toString();
        this.bookService.save(Book.builder()
                .isbn(isbn)
                .price(new BigDecimal("10.99"))
                .name("Elastic Stack 7.0")
                .description("A beginner's guide to storing, managing, and analyzing data with the updated features of Elastic 7.0")
                .build());

        final Optional<Book> book = this.bookService.findById(isbn);

        this.assertBookInfo(book.get(), "Elastic Stack 7.0", "A beginner's guide to storing, managing, and analyzing data with the updated features of Elastic 7.0", "10.99");
    }

    @Test
    void testFindBookName() {
        final String isbn = UUID.randomUUID().toString();
        final String isbn2 = UUID.randomUUID().toString();

        this.bookService.save(Book.builder()
                .isbn(isbn).price(new BigDecimal("30.00")).name("Java 8 definitive guide")
                .description("You’ll learn through code examples, exercises, and fluid explanations how these anonymous functions will help you write simple").build());

        this.bookService.save(Book.builder()
                .isbn(isbn2).price(new BigDecimal("15.33")).name("NodeJS definitive guide")
                .description("all detail about node").build());

        final List<Book> java = this.bookService.findByFilter(Book.builder().name("java").build());

        Assert.assertNotNull(java);
        Assert.assertEquals(1, java.size());

        this.assertBookInfo(java.stream().findFirst().get(), "Java 8 definitive guide",
                "You’ll learn through code examples, exercises, and fluid explanations how these anonymous functions will help you write simple",
                "30.0");

        final List<Book> books = this.bookService.findByFilter(Book.builder().name("definitive").build());
        Assert.assertNotNull(books);
        Assert.assertEquals(2, books.size());
    }

    @Test
    void testFindBookDescription() {
        final String isbn = UUID.randomUUID().toString();
        final String isbn2 = UUID.randomUUID().toString();

        this.bookService.save(Book.builder()
                .isbn(isbn).price(new BigDecimal("22.00")).name("Kotlin")
                .description("Kotlin is a new programming language targeting the Java platform").build());

        this.bookService.save(Book.builder()
                .isbn(isbn2).price(new BigDecimal("15.00")).name("angular")
                .description("angular is a new programming language targeting the Node").build());

        final List<Book> books = this.bookService.findByFilter(Book.builder().description("programming").build());
        Assert.assertNotNull(books);
        Assert.assertEquals(2, books.size());
    }


    private void assertBookInfo(Book book, String name, String description, String price) {
        Assert.assertNotNull(book);
        Assert.assertEquals(name, book.getName());
        Assert.assertEquals(description, book.getDescription());
        Assert.assertEquals(price, book.getPrice().toString());
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }

}
