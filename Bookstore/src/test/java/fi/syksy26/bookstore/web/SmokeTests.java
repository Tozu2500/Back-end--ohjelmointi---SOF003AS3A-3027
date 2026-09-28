package fi.syksy26.bookstore.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import fi.syksy26.bookstore.domain.Book;
import fi.syksy26.bookstore.domain.BookRepository;

// Savutestit: tarkistavat nopeasti, etta sovelluksen kaikki controllerit ja
// niiden osoitteet vastaavat. Ei kata yksityiskohtia (ne testataan
// SecurityTests- ja BookValidationTests-luokissa), vaan varmistaa etta
// esimerkkidata, sivut ja REST-rajapinnat ovat pystyssa.
// Pyynnot tehdaan kirjautuneena, koska kaikki osoitteet vaativat kirjautumisen.
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class SmokeTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookController bookController;

	@Autowired
	private BookRestController bookRestController;

	@Autowired
	private BookRepository bookRepository;

	// Perusmuotoinen savutesti: molemmat controllerit loytyvat Springin kontekstista
	@Test
	void controllersAreLoaded() {
		assertThat(bookController).isNotNull();
		assertThat(bookRestController).isNotNull();
	}

	@Test
	void exampleDataIsLoadedToDatabase() {
		List<Book> found = bookRepository.findByTitle("Clean Code");
		assertThat(found).hasSize(1);
		assertThat(found.get(0).getAuthor()).isEqualTo("Robert C. Martin");
		assertThat(found.get(0).getCategory().getName()).isEqualTo("Programming");
	}

	// --- BookController ---

	@Test
	void loginPageOpens() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}

	@Test
	void frontPageRedirectsToBookList() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/booklist"));
	}

	@Test
	void indexPageShowsBooks() throws Exception {
		mockMvc.perform(get("/index"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attributeExists("books"))
			.andExpect(content().string(containsString("The Hobbit")));
	}

	@Test
	void bookListPageShowsBooks() throws Exception {
		mockMvc.perform(get("/booklist"))
			.andExpect(status().isOk())
			.andExpect(view().name("booklist"))
			.andExpect(model().attributeExists("books"))
			.andExpect(content().string(containsString("The Hobbit")));
	}

	// Lisayslomakkeella pitaa olla tyhja kirja ja kategoriat pudotusvalikkoon
	@Test
	void addBookFormOpensWithCategories() throws Exception {
		mockMvc.perform(get("/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("addbook"))
			.andExpect(model().attributeExists("book", "categories"))
			.andExpect(content().string(containsString("Fantasy")));
	}

	// Muokkauslomakkeelle pitaa latautua valitun kirjan tiedot
	@Test
	void editBookFormOpensWithSelectedBook() throws Exception {
		// Haetaan id tietokannasta, koska muut testit voivat lisata kirjoja
		Long id = bookRepository.findByTitle("The Hobbit").get(0).getId();
		mockMvc.perform(get("/edit/" + id))
			.andExpect(status().isOk())
			.andExpect(view().name("editbook"))
			.andExpect(model().attributeExists("book", "categories"))
			.andExpect(content().string(containsString("The Hobbit")));
	}

	// --- BookRestController ---

	@Test
	void restControllerReturnsBooksAsJson() throws Exception {
		mockMvc.perform(get("/books"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[?(@.title=='The Hobbit')].author").value("J.R.R. Tolkien"));
	}

	@Test
	void restControllerReturnsOneBookById() throws Exception {
		Long id = bookRepository.findByTitle("The Hobbit").get(0).getId();
		mockMvc.perform(get("/book/" + id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.isbn").value("978-0-261-10221-4"))
			// Kategoria naytetaan, mutta sen kirjalista on jatetty pois JSON:sta (@JsonIgnore)
			.andExpect(jsonPath("$.category.name").value("Fantasy"))
			.andExpect(jsonPath("$.category.books").doesNotExist());
	}

	// --- Spring Data REST (/api) ---

	@Test
	void springDataRestApiIsAvailable() throws Exception {
		mockMvc.perform(get("/api/books"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.books").isArray());
		mockMvc.perform(get("/api/categories"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.categories").isArray());
	}

	// Spring Data RESTin hakumetodi BookRepositorysta
	@Test
	void springDataRestSearchWorks() throws Exception {
		mockMvc.perform(get("/api/books/search/findByAuthor").param("author", "Aleksis Kivi"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.books[0].title").value("Seitseman veljesta"));
	}

}
