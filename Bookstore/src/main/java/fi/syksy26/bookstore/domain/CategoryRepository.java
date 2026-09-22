package fi.syksy26.bookstore.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // Hakumetodi Spring Data RESTiin: /api/categories/search/findByName?name=Fantasy
    List<Category> findByName(String name);

}
