package fi.syksy26.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.syksy26.bookstore.domain.Book;
import fi.syksy26.bookstore.domain.BookRepository;
import fi.syksy26.bookstore.domain.Category;
import fi.syksy26.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookstoreRunner(BookRepository bookRepository, CategoryRepository categoryRepository) {
		return (args) -> {
			log.info("Tallennetaan esimerkkikategoriat tietokantaan");
			Category fantasy = categoryRepository.save(new Category("Fantasy"));
			Category programming = categoryRepository.save(new Category("Programming"));
			Category classics = categoryRepository.save(new Category("Classics"));

			log.info("Tallennetaan esimerkkikirjat tietokantaan");
			bookRepository.save(new Book("The Hobbit", "J.R.R. Tolkien", 1937, "978-0-261-10221-4", 15.90, fantasy));
			bookRepository.save(new Book("Clean Code", "Robert C. Martin", 2008, "978-0-13-235088-4", 42.50, programming));
			bookRepository.save(new Book("Seitseman veljesta", "Aleksis Kivi", 1870, "978-951-1-20876-2", 22.00, classics));

			log.info("Tietokannan kategoriat:");
			for (Category category : categoryRepository.findAll()) {
				log.info(category.toString());
			}

			log.info("Tietokannan kirjat:");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}
		};
	}

}
