package fi.syksy26.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.syksy26.bookstore.domain.Book;
import fi.syksy26.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookstoreRunner(BookRepository bookRepository) {
		return (args) -> {
			log.info("Tallennetaan esimerkkikirjat tietokantaan");
			bookRepository.save(new Book("The Hobbit", "J.R.R. Tolkien", 1937, "978-0-261-10221-4", 15.90));
			bookRepository.save(new Book("Clean Code", "Robert C. Martin", 2008, "978-0-13-235088-4", 42.50));
			bookRepository.save(new Book("Seitseman veljesta", "Aleksis Kivi", 1870, "978-951-1-20876-2", 22.00));

			log.info("Tietokannan kirjat:");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}
		};
	}

}
