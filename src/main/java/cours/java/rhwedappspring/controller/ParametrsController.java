package cours.java.rhwedappspring.controller;

import cours.java.rhwedappspring.dao.SpecialiteRepository;
import cours.java.rhwedappspring.model.Specialite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/parametre")
public class ParametrsController {

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @GetMapping("/service/{id}")
    public @ResponseBody List<Specialite> specialitesByService(@PathVariable(name = "id") int serviceId){
        return specialiteRepository.findByService_Id(serviceId);
    }
}
