package fi.syksy26.bookstore.web;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fi.syksy26.bookstore.domain.Book;
import fi.syksy26.bookstore.domain.BookRepository;

// @RestController = @Controller + @ResponseBody:
// jokainen metodi palauttaa datan (JSON) nakyman nimen sijaan.
@RestController
public class BookRestController {

    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Palauttaa kaikki kirjat JSON-muodossa
    @GetMapping("/books")
    public Iterable<Book> bookListRest() {
        return bookRepository.findAll();
    }

    // Palauttaa yhden kirjan id:n perusteella JSON-muodossa
    @GetMapping("/book/{id}")
    public Optional<Book> findBookRest(@PathVariable("id") Long bookId) {
        return bookRepository.findById(bookId);
    }

}
