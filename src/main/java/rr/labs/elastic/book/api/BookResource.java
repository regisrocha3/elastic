package rr.labs.elastic.book.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rr.labs.elastic.book.domain.Book;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResource {
    @NotEmpty(message = "Nome do livro é obrigatório")
    private String name;
    @NotEmpty(message = "ISBN do livro é obrigatório")
    private String isbn;
    private String description;
    private BigDecimal price;

    public Book toDomain() {
        return Book.builder()
                .description(this.description)
                .isbn(this.isbn)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
