package fi.syksy26.bookstore.web;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import fi.syksy26.bookstore.domain.Book;
import fi.syksy26.bookstore.domain.BookRepository;

// Smoke testejä: tarkistavat nopeasti, etta sovelluksen perustoiminnot vastaavat.
// Ei kata yksityiskohtia, vaan varmistaa etta esimerkkidata, sivut ja
// REST-rajapinnat ovat pystyssa. Kirjautuneena kayttajana, koska kaikki
// osoitteet vaativat kirjautumisen.
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class SmokeTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	@Test
	void exampleDataIsLoadedToDatabase() {
		List<Book> found = bookRepository.findByTitle("Clean Code");
		assertThat(found).hasSize(1);
		assertThat(found.get(0).getAuthor()).isEqualTo("Robert C. Martin");
		assertThat(found.get(0).getCategory().getName()).isEqualTo("Programming");
	}

	@Test
	void bookListPageShowsBooks() throws Exception {
		mockMvc.perform(get("/booklist"))
			.andExpect(status().isOk())
			.andExpect(view().name("booklist"))
			.andExpect(model().attributeExists("books"))
			.andExpect(content().string(containsString("The Hobbit")));
	}

	@Test
	void restControllerReturnsBooksAsJson() throws Exception {
		mockMvc.perform(get("/books"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[?(@.title=='The Hobbit')].author").value("J.R.R. Tolkien"));

		// Haetaan id tietokannasta, koska muut testit voivat lisata kirjoja
		Long id = bookRepository.findByTitle("The Hobbit").get(0).getId();
		mockMvc.perform(get("/book/" + id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.isbn").value("978-0-261-10221-4"));
	}

	@Test
	void springDataRestApiIsAvailable() throws Exception {
		mockMvc.perform(get("/api/books"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.books").isArray());
	}

}
