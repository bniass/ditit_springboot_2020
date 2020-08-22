package cours.java.rhwedappspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/403")
    public String notfound(Model model){
        return "403";
    }

}
