package fi.syksy26.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.syksy26.bookstore.domain.BookRepository;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/index")
    public String showBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index"; // index.html
    }

}
