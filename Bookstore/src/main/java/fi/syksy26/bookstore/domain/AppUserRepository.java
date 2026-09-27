package fi.syksy26.bookstore.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// exported = false: Spring Data REST ei julkaise kayttajia (eika salasanatiivisteita)
// osoitteessa /api, eika kayttajia voi luoda tai muokata sen kautta
@RepositoryRestResource(exported = false)
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

}
