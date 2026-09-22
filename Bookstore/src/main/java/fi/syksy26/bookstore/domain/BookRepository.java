package fi.syksy26.bookstore.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    // Spring Data REST julkaisee nama hakumetodit osoitteessa
    // http://localhost:8100/api/books/search
    // esim. /api/books/search/findByTitle?title=Clean Code
    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

}
