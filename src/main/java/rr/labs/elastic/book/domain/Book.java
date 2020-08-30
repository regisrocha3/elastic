package rr.labs.elastic.book.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "books")
public class Book {
    @Id
    private String isbn;
    private String name;
    private String description;
    private BigDecimal price;
}
