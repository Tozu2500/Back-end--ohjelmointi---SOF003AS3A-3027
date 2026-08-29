package fi.syksy26.bookstore.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.syksy26.bookstore.domain.Book;

@Controller
public class BookController {

    @GetMapping("/index")
    public String showBooks(Model model) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", 1937, "978-0-261-10221-4", 15.90));
        books.add(new Book("Clean Code", "Robert C. Martin", 2008, "978-0-13-235088-4", 42.50));
        books.add(new Book("Seitseman veljesta", "Aleksis Kivi", 1870, "978-951-1-20876-2", 22.00));

        model.addAttribute("books", books);
        return "index"; // index.html
    }

}
