package cours.java.rhwedappspring.controller;

import cours.java.rhwedappspring.dao.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medecin")
public class MedecinController {

    @Autowired
    private MedecinRepository medecinRepository;

    @GetMapping("/all")
    public String medecinPage(Model model) {
        model.addAttribute("medecins", medecinRepository.findAll());
        return "medecin";
    }
}
