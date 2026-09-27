package fi.syksy26.bookstore.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

// Testaa kirjautumisen tietokannan kayttajilla seka roolien kayttooikeudet
@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void anonymousUserIsRedirectedToLogin() throws Exception {
		mockMvc.perform(get("/booklist"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/login"));
	}

	@Test
	void loginPageAndStylesArePublic() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
		mockMvc.perform(get("/css/bootstrap.min.css"))
			.andExpect(status().isOk());
	}

	@Test
	void databaseUsersCanLogIn() throws Exception {
		mockMvc.perform(formLogin().user("admin").password("admin"))
			.andExpect(authenticated().withUsername("admin").withRoles("ADMIN"))
			.andExpect(redirectedUrl("/booklist"));
		mockMvc.perform(formLogin().user("user").password("user"))
			.andExpect(authenticated().withUsername("user").withRoles("USER"));
	}

	@Test
	void wrongPasswordIsRejected() throws Exception {
		mockMvc.perform(formLogin().user("admin").password("wrong"))
			.andExpect(unauthenticated())
			.andExpect(redirectedUrl("/login?error"));
	}

	@Test
	void logoutRedirectsToLoginPage() throws Exception {
		mockMvc.perform(logout())
			.andExpect(unauthenticated())
			.andExpect(redirectedUrl("/login?logout"));
	}

	@Test
	@WithMockUser(username = "testuser", roles = "USER")
	void userSeesUsernameButNoDeleteButton() throws Exception {
		mockMvc.perform(get("/booklist"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("testuser")))
			.andExpect(content().string(not(containsString("Delete"))));
	}

	@Test
	@WithMockUser(username = "testadmin", roles = "ADMIN")
	void adminSeesDeleteButton() throws Exception {
		mockMvc.perform(get("/booklist"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("testadmin")))
			.andExpect(content().string(containsString("Delete")));
	}

	@Test
	@WithMockUser(roles = "USER")
	void userCannotDeleteBooks() throws Exception {
		mockMvc.perform(get("/delete/1"))
			.andExpect(status().isForbidden());
		mockMvc.perform(delete("/api/books/1").with(csrf()))
			.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void adminCanDeleteBooks() throws Exception {
		// Olematon id: testataan vain kayttooikeus poistamatta esimerkkidataa
		mockMvc.perform(get("/delete/999"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/booklist"));
	}

}
