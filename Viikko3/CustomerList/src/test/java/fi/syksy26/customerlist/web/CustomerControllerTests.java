package fi.syksy26.customerlist.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

// Testaa listasivun seka /save-lomakkeen validoinnin
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void customerListShowsCustomers() throws Exception {
		mockMvc.perform(get("/customerlist"))
			.andExpect(status().isOk())
			.andExpect(view().name("customerlist"))
			.andExpect(model().attributeExists("customers"));
	}

	@Test
	void emptyFormReturnsToAddFormWithErrors() throws Exception {
		mockMvc.perform(post("/save")
				.param("name", "")
				.param("email", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("addcustomer"))
			.andExpect(model().attributeHasFieldErrors("customer", "name", "email"));
	}

	@Test
	void invalidEmailIsRejected() throws Exception {
		mockMvc.perform(post("/save")
				.param("name", "Test Customer")
				.param("email", "not-an-email"))
			.andExpect(view().name("addcustomer"))
			.andExpect(model().attributeHasFieldErrors("customer", "email"))
			.andExpect(model().attributeErrorCount("customer", 1));
	}

	@Test
	void validCustomerIsSavedAndRedirects() throws Exception {
		mockMvc.perform(post("/save")
				.param("name", "Test Customer")
				.param("email", "test.customer@example.com"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/customerlist"));
	}

}
