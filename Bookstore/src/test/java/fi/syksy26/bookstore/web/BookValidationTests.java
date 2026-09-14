package fi.syksy26.bookstore.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

// Testaa, etta /save hylkaa virheelliset syotteet ja hyvaksyy kelvolliset
@SpringBootTest
@AutoConfigureMockMvc
class BookValidationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void emptyFormReturnsToAddFormWithErrors() throws Exception {
		mockMvc.perform(post("/save")
				.param("title", "")
				.param("author", "")
				.param("publicationYear", "0")
				.param("isbn", "")
				.param("price", "-1"))
			.andExpect(status().isOk())
			.andExpect(view().name("addbook"))
			.andExpect(model().attributeHasFieldErrors("book",
					"title", "author", "publicationYear", "isbn", "price", "category"));
	}

	@Test
	void invalidIsbnIsRejected() throws Exception {
		mockMvc.perform(post("/save")
				.param("title", "Test")
				.param("author", "Author")
				.param("publicationYear", "2000")
				.param("isbn", "abc-123")
				.param("price", "10")
				.param("category", "1"))
			.andExpect(view().name("addbook"))
			.andExpect(model().attributeHasFieldErrors("book", "isbn"))
			.andExpect(model().attributeErrorCount("book", 1));
	}

	@Test
	void validBookIsSavedAndRedirects() throws Exception {
		mockMvc.perform(post("/save")
				.param("title", "Test")
				.param("author", "Author")
				.param("publicationYear", "2000")
				.param("isbn", "978-0-261-10221-4")
				.param("price", "10")
				.param("category", "1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/booklist"));
	}

	@Test
	void editWithErrorsReturnsToEditForm() throws Exception {
		mockMvc.perform(post("/save")
				.param("id", "1")
				.param("title", "")
				.param("author", "Author")
				.param("publicationYear", "2000")
				.param("isbn", "978-0-261-10221-4")
				.param("price", "10")
				.param("category", "1"))
			.andExpect(view().name("editbook"))
			.andExpect(model().attributeHasFieldErrors("book", "title"));
	}
}
