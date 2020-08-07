package cours.java.rhwedappspring.controller;

import cours.java.rhwedappspring.dao.MedecinRepository;
import cours.java.rhwedappspring.dao.ServiceRepository;
import cours.java.rhwedappspring.dao.SpecialiteRepository;
import cours.java.rhwedappspring.model.Medecin;
import cours.java.rhwedappspring.model.Service;
import cours.java.rhwedappspring.model.Specialite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/medecin")
public class MedecinController {

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @GetMapping("/all")
    public String medecinPage(Model model) {
        Medecin medecin = new Medecin();
        medecin.setService(new Service());
        medecin.setSpecialites(new ArrayList<>());
        model.addAttribute("medecin", medecin);
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("medecins", medecinRepository.findAll());
        return "medecin";
    }

    @PostMapping("/add")
    public String addmedecin(@ModelAttribute("medecin") Medecin medecin, long[] specialite) {
        List<Specialite> specialiteList = new ArrayList<>();
        for (long sp: specialite) {
            specialiteList.add(specialiteRepository.findById(sp).get());
        }
        medecin.setSpecialites(specialiteList);
        medecinRepository.save(medecin);
        return "redirect:/medecin/all";
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Medecin OneMedecin(@PathVariable(name = "id") long medecinId){
        return medecinRepository.findById(medecinId).get();
    }

}
