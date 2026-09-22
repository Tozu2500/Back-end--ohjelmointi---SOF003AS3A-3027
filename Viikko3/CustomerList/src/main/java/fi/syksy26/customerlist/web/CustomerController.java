package fi.syksy26.customerlist.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.syksy26.customerlist.domain.Customer;
import fi.syksy26.customerlist.domain.CustomerRepository;
import jakarta.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Etusivu ohjaa asiakaslistaan
    @GetMapping("/")
    public String home() {
        return "redirect:/customerlist";
    }

    // Listaa kaikki asiakkaat tietokannasta
    @GetMapping("/customerlist")
    public String customerList(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customerlist"; // customerlist.html
    }

    // Avaa lomakkeen uuden asiakkaan lisaamiseksi
    @GetMapping("/add")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "addcustomer"; // addcustomer.html
    }

    // Tallentaa uuden tai muokatun asiakkaan
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return customer.getId() == null ? "addcustomer" : "editcustomer";
        }
        customerRepository.save(customer);
        return "redirect:/customerlist";
    }

    // Poistaa asiakkaan id:n perusteella
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long customerId) {
        customerRepository.deleteById(customerId);
        return "redirect:/customerlist";
    }

    // Avaa nykyisen asiakkaan muokkauslomakkeeseen
    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") Long customerId, Model model) {
        model.addAttribute("customer", customerRepository.findById(customerId).orElseThrow());
        return "editcustomer"; // editcustomer.html
    }

}
