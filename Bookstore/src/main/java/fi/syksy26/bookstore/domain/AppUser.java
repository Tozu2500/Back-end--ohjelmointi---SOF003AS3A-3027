package fi.syksy26.bookstore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Sovelluksen loppukayttaja. Luokka ei ole nimeltaan "User", koska USER on
// varattu sana SQL:ssa ja Spring Securityssa on jo oma User-luokka.
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    // Tietokantaan tallennetaan vain salasanan BCrypt-tiiviste, ei itse salasanaa
    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String email;

    // Rooli ilman ROLE_-etuliitetta, esim. "USER" tai "ADMIN"
    @Column(nullable = false)
    private String role;

    public AppUser() {
    }

    public AppUser(String username, String passwordHash, String email, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
