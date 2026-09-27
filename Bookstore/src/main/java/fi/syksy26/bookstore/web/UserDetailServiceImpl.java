package fi.syksy26.bookstore.web;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.syksy26.bookstore.domain.AppUser;
import fi.syksy26.bookstore.domain.AppUserRepository;

// Spring Security hakee kirjautuvan kayttajan tietokannasta taman palvelun avulla
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public UserDetailServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // roles() lisaa roolille ROLE_-etuliitteen, joten hasRole('ADMIN') toimii
        return User.withUsername(appUser.getUsername())
                .password(appUser.getPasswordHash())
                .roles(appUser.getRole())
                .build();
    }

}
