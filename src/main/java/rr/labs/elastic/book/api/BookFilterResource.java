package rr.labs.elastic.book.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rr.labs.elastic.book.domain.Book;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookFilterResource {
    private String name;
    private String isbn;
    private String description;

    public Book toDomain() {
        return Book.builder()
                .description(this.description)
                .name(this.name)
                .isbn(this.isbn)
                .build();
    }
}
