package fi.syksy26.customerlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.syksy26.customerlist.domain.Customer;
import fi.syksy26.customerlist.domain.CustomerRepository;

@SpringBootApplication
public class CustomerListApplication {

	private static final Logger log = LoggerFactory.getLogger(CustomerListApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CustomerListApplication.class, args);
	}

	@Bean
	public CommandLineRunner customerRunner(CustomerRepository customerRepository) {
		return (args) -> {
			// Lisataan esimerkkiasiakkaat vain, jos taulu on viela tyhja.
			// MySQL sailyttaa datan kaynnistysten valilla, toisin kuin H2-muistikanta.
			if (customerRepository.count() == 0) {
				log.info("Tallennetaan esimerkkiasiakkaat tietokantaan");
				customerRepository.save(new Customer("Matti Meikalainen", "matti.meikalainen@example.com"));
				customerRepository.save(new Customer("Maija Virtanen", "maija.virtanen@example.com"));
				customerRepository.save(new Customer("John Smith", "john.smith@example.com"));
			}

			log.info("Tietokannan asiakkaat:");
			for (Customer customer : customerRepository.findAll()) {
				log.info(customer.toString());
			}
		};
	}

}
