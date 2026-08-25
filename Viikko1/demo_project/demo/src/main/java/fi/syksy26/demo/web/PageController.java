package fi.syksy26.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    @RequestMapping("/index")
    @ResponseBody
    public String returnIndexPage() {
        return "This is the main page";
    }

    @RequestMapping("/contact")
    @ResponseBody
    public String returnContactPage() {
        return "This is the contact page";
    }

}
