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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/medecin")
public class MedecinController {
    private static String UPLOADED_FOLDER = "D://mes donnees//uploadFileSpring//";


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
        byte[] bytes = {};
        Path path = null;
        try {
            if(!medecin.getFiles()[0].getName().equals("")){
                MultipartFile file = medecin.getFiles()[0];
                bytes = file.getBytes();
                path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                medecin.setPhoto(file.getOriginalFilename());
            }
            else{
                medecin.setPhoto("default.jpeg");
            }
            medecin.setSpecialites(specialiteList);
            medecinRepository.save(medecin);
            if(bytes.length != 0) {
                Files.write(path, bytes);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return "redirect:/medecin/all";
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Medecin OneMedecin(@PathVariable(name = "id") long medecinId){
        return medecinRepository.findById(medecinId).get();
    }

    @RequestMapping(value = "image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
        System.out.println("called");
        File serverFile = new File("D://mes donnees//uploadFileSpring//" + imageName + ".jpg");
        return Files.readAllBytes(serverFile.toPath());
    }

    @PostMapping("/transfert")
    public String addmedecin(long id, long serviceId) {
        Medecin m = medecinRepository.getOne(id);
        Service s = serviceRepository.getOne(serviceId);
        m.setService(s);
        medecinRepository.save(m);
        return "redirect:/medecin/all";
    }
    @PostMapping("/manage")
    public String addmedecin(long id, long[] specialite) {
        Medecin m = medecinRepository.getOne(id);
        List<Specialite> specialiteList = new ArrayList<>();
        for (long sp: specialite) {
            specialiteList.add(specialiteRepository.findById(sp).get());
        }
        m.setSpecialites(specialiteList);
        medecinRepository.save(m);
        return "redirect:/medecin/all";
    }
    @PostMapping("/remove")
    public String addmedecin(long medecinId) {
        Medecin m = medecinRepository.getOne(medecinId);
        medecinRepository.delete(m);
        return "redirect:/medecin/all";
    }
}
