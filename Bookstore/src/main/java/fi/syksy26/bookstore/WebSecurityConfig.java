package fi.syksy26.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // mahdollistaa @PreAuthorize-annotaatiot controllereissa
public class WebSecurityConfig {

    // Kirjautuminen kayttaa tietokannan kayttajia (AppUser) muistinvaraisten kayttajien sijaan:
    // Spring Security hakee kayttajat UserDetailServiceImpl-palvelulla (ainoa UserDetailsService-bean)
    // ja vertaa salasanaa tietokantaan tallennettuun BCrypt-tiivisteeseen taman beanin avulla.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Tyylit sallitaan, jotta kirjautumissivu nakyy oikein ennen kirjautumista
                .requestMatchers("/css/**").permitAll()
                // H2-konsolilla voi muokata tietokantaa (myos kayttajien rooleja), joten vain ADMIN
                .requestMatchers("/h2-console/**").hasRole("ADMIN")
                // Poisto REST-rajapinnan kautta (esim. DELETE /api/books/1) vain ADMIN-kayttajille
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                // Kaikki muut osoitteet vaativat kirjautumisen
                .anyRequest().authenticated()
            )
            .formLogin(formlogin -> formlogin
                .loginPage("/login")
                .defaultSuccessUrl("/booklist", true)
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            )
            // H2-konsolin lomakkeissa ei ole CSRF-tunnistetta, ja konsoli kayttaa kehyksia (frame)
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }

}
