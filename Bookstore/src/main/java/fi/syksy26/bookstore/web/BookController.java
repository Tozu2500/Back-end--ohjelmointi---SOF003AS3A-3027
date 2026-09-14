package fi.syksy26.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.syksy26.bookstore.domain.Book;
import fi.syksy26.bookstore.domain.BookRepository;
import fi.syksy26.bookstore.domain.CategoryRepository;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/index")
    public String showBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index"; // index.html
    }

    // Listaa kaikki kirjat tietokannasta
    @GetMapping("/booklist")
    public String bookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist"; // booklist.html
    }

    // Avaa lomakkeen uuden kirjan lisaamiseksi
    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook"; // addbook.html
    }

    // Tallentaa uuden tai muokatun kirjan
    @PostMapping("/save")
    public String save(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    // Poistaa kirjan id:n perusteella
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId) {
        bookRepository.deleteById(bookId);
        return "redirect:/booklist";
    }

    // Avaa nykyisen kirjan muokkauslomakkeeseen
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("book", bookRepository.findById(bookId).orElseThrow());
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook"; // editbook.html
    }

}
