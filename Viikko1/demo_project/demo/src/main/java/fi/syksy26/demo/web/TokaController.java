package fi.syksy26.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokaController {

    @RequestMapping("/kukka")
    @ResponseBody
    public String sayMessage() {
        return "Heissan";
    }

    @RequestMapping("/sayHello")
    @ResponseBody
    public String returnGreeting(@RequestParam (name="nimesi", required = false, defaultValue = "Muumipeikko") String etunimi) {
        return "Hei " + etunimi;
    }

    @RequestMapping("/sayHelloAndAge")
    @ResponseBody
    public String returnAgeGreeting(@RequestParam (name = "nimesi", required = false, defaultValue = "Muumipeikko") String etunimi,
                @RequestParam int age) {
        return "Hei " + etunimi + ", " + age + " vuotta";
    }

}
