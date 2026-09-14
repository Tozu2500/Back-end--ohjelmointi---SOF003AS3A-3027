package fi.syksy26.bookstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Validointisaannot: tarkistetaan lomakkeen tallennuksessa (@Valid controllerissa)
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author must be at most 100 characters")
    private String author;

    // HUOM: ei saa olla "year", koska se on varattu sana SQL:ssa
    @Min(value = 1450, message = "Year must be 1450 or later")
    @Max(value = 2100, message = "Year must be 2100 or earlier")
    private int publicationYear;

    // ISBN: 10 tai 13 numeroa, valissa saa olla viivoja tai valilyonteja (esim. 978-0-261-10221-4)
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:\\d[- ]*){9}\\d$|^(?:\\d[- ]*){12}\\d$",
             message = "ISBN must contain 10 or 13 digits (hyphens and spaces allowed)")
    private String isbn;

    @PositiveOrZero(message = "Price cannot be negative")
    @Max(value = 10000, message = "Price must be at most 10000")
    private double price;

    // Monta kirjaa voi kuulua yhteen kategoriaan
    @NotNull(message = "Category is required")
    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    public Book() {
    }

    public Book(String title, String author, int publicationYear, String isbn, double price) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.price = price;
    }

    public Book(String title, String author, int publicationYear, String isbn, double price, Category category) {
        this(title, author, publicationYear, isbn, price);
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", publicationYear=" + publicationYear
                + ", isbn=" + isbn + ", price=" + price + ", category=" + (category != null ? category.getName() : null) + "]";
    }

}
