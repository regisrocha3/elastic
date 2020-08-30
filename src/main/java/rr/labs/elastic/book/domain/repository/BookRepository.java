package rr.labs.elastic.book.domain.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rr.labs.elastic.book.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {

    @Query("{\"bool\" : {\"should\" : [{ \"term\" : { \"name\" : \"?0\" } },{ \"term\" : { \"description\" : \"?1\" }},{ \"term\" : { \"isbn\" : \"?2\" }}] }}")
    List<Book> findByFilter(String name, String description, String isbn);
}
