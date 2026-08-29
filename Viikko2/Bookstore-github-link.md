# Bookstore-projektin GitHub-linkki

Bookstore-projekti (SP2, tehtava 5) sijaitsee tassa samassa kurssirepositoriossa
kansiossa `Viikko2/Bookstore`.

## Linkki projektiin

https://github.com/Tozu2500/Back-end--ohjelmointi---SOF003AS3A-3027/tree/Projects/Viikko2/Bookstore

## Linkki koko repositorioon

https://github.com/Tozu2500/Back-end--ohjelmointi---SOF003AS3A-3027

## Projektin sisalto

- `fi.syksy26.bookstore.BookstoreApplication` - Spring Boot -sovelluksen kaynnistysluokka
- `fi.syksy26.bookstore.domain.Book` - malliluokka (title, author, publicationYear, isbn, price)
- `fi.syksy26.bookstore.web.BookController` - kasittelee GET-pyynnon polkuun `/index`
- `src/main/resources/templates/index.html` - Thymeleaf-nakyma, joka listaa kirjat

Sovellus kaynnistetaan komennolla `mvn spring-boot:run` ja se vastaa osoitteessa
http://localhost:8080/index
