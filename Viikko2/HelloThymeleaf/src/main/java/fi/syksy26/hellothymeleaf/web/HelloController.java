package fi.syksy26.hellothymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam String name, @RequestParam int age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "hello"; // hello.html
    }

}
